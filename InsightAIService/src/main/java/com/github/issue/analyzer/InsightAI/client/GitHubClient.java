package com.github.issue.analyzer.InsightAI.client;

import com.github.issue.analyzer.InsightAI.model.dto.GithubIssueDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;


public class GitHubClient {

    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GithubIssueDTO> fetchIssues(String repo) {
        return webClient.get()
                .uri("/repos/"+repo+"/issues")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<GithubIssueDTO>>() {})
                .block();

    }


}
