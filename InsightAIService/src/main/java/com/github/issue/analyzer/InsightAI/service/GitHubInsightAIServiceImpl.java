package com.github.issue.analyzer.InsightAI.service;

import com.github.issue.analyzer.InsightAI.client.GitHubClient;
import com.github.issue.analyzer.InsightAI.client.LlmClient;
import com.github.issue.analyzer.InsightAI.model.GithubIssue;
import com.github.issue.analyzer.InsightAI.model.dto.GitHubIssueScanResponse;
import com.github.issue.analyzer.InsightAI.model.dto.GithubIssueDTO;
import com.github.issue.analyzer.InsightAI.repository.GithubIssueRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GitHubInsightAIServiceImpl implements GitHubInsightAIService {

    private static final Logger log = LoggerFactory.getLogger(GitHubInsightAIServiceImpl.class);


    private final GithubIssueRepository githubIssueRepository;
    private final GitHubClient gitHubClient;

    private final LlmClient llmClient;

    public GitHubInsightAIServiceImpl(GithubIssueRepository githubIssueRepository,
                                      GitHubClient gitHubClient,
                                      LlmClient llmClient) {
        this.githubIssueRepository = githubIssueRepository;
        this.gitHubClient = gitHubClient;
        this.llmClient = llmClient;
    }

    @Override
    public GitHubIssueScanResponse scanAndStoreIssues(String repo) {

        log.info("Starting issue scan for repo: {}", repo);
        List<GithubIssueDTO> issues = gitHubClient.fetchIssues(repo);

        int storedCount = 0;

        for (GithubIssueDTO dto : issues) {


            Optional<GithubIssue> existing =
                    githubIssueRepository.findByGithubIdAndRepo(dto.getId(), repo);

            if (existing.isPresent()) {
                GithubIssue issue = existing.get();
                issue.setTitle(dto.getTitle());
                issue.setBody(dto.getBody() == null ? "" : dto.getBody());
                issue.setHtmlUrl(dto.getHtmlUrl());
                issue.setCreatedAt(dto.getCreatedAt().toLocalDateTime());

                githubIssueRepository.save(issue);

            } else {
                GithubIssue newIssue = mapToEntity(dto, repo);
                githubIssueRepository.save(newIssue);
            }

            storedCount++;
        }
        log.info("Completed issue scan for repo: {}. Total issues fetched: {}, Stored/Updated: {}",
                repo, issues.size(), storedCount);

        return new GitHubIssueScanResponse(repo, issues.size(), "Issues scanned and stored successfully.");
    }

    private GithubIssue mapToEntity(GithubIssueDTO dto, String repo) {
        return GithubIssue.builder()
                .githubId(dto.getId())
                .title(dto.getTitle())
                .body(dto.getBody() == null ? "" : dto.getBody())
                .htmlUrl(dto.getHtmlUrl())
                .createdAt(dto.getCreatedAt().toLocalDateTime())
                .state(dto.getState())
                .repo(repo)
                .build();
    }

    @Override
    public String analyzeIssues(String repo, String prompt) {

        List<GithubIssue> issues = githubIssueRepository.findByRepo(repo);

        if (issues == null || issues.isEmpty()) {
            return "No issues found for this repository. Please run /scan first.";
        }

        String context = filterRelevantIssues(issues, prompt).stream()
                .map(issue ->
                        "Title: " + issue.getTitle() + "\n" +
                                "Description: " + issue.getBody().substring(0, Math.min(500, issue.getBody().length())) + "..."
                )
                .limit(10)
                .collect(Collectors.joining("\n---\n"));

        String systemPrompt = """
                You are a senior software engineer analyzing GitHub issues.
                Return plain text only.
                Do NOT use markdown, headers (##), bullet points, bold (**), or any special characters.
                Do NOT use \\n escape sequences.
                Write in clean, natural prose paragraphs only.
                Separate sections with a blank line if needed.
                """;

        String userPrompt = "User Request: " + prompt + "\n\nIssues:\n" + context;
        String response = llmClient.analyzeIssueWithLLM(systemPrompt, userPrompt);

        return cleanResponse(response);

    }


    private List<GithubIssue> filterRelevantIssues(List<GithubIssue> issues, String prompt) {
        String lowerPrompt = prompt.toLowerCase();

        return issues.stream()
                .filter(issue -> {
                    String title = issue.getTitle().toLowerCase();
                    if (lowerPrompt.contains("bug")) {
                        return title.contains("bug") || title.contains("error")
                                || title.contains("fix") || title.contains("crash");
                    }

                    if (lowerPrompt.contains("feature")) {
                        return title.contains("feature") || title.contains("enhancement")
                                || title.contains("request");
                    }

                    return "open".equalsIgnoreCase(issue.getState());
                })
                .limit(30)
                .collect(Collectors.toList());
    }

    // Safety net cleaner
    private String cleanResponse(String response) {
        return response
                .replaceAll("\\*\\*([^*]+)\\*\\*", "$1")  // Remove **bold**
                .replaceAll("\\*([^*]+)\\*", "$1")          // Remove *italic*
                .replaceAll("#{1,6}\\s?", "")               // Remove headers
                .replaceAll("```[\\s\\S]*?```", "")          // Remove code blocks
                .replaceAll("\\n{2,}", " ")                  // Collapse double newlines
                .replaceAll("\\n", " ")                      // Remove single newlines
                .replaceAll("[-•]\\s", "")                   // Remove bullets
                .replaceAll("\\|.*?\\|", "")                 // Remove table rows
                .replaceAll("\\s{2,}", " ")                  // Collapse extra spaces
                .trim();
    }
}
