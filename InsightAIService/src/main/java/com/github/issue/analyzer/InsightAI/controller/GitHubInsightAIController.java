package com.github.issue.analyzer.InsightAI.controller;

import com.github.issue.analyzer.InsightAI.model.dto.GitHubIssueScanResponse;
import com.github.issue.analyzer.InsightAI.service.GitHubInsightAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/githubinsightai")
@Tag(name = "GitHub Analyzer", description = "Scan and Analyze GitHub Issues")
public class GitHubInsightAIController {

    @Resource
    private GitHubInsightAIService gitHubInsightAIService;

    @PostMapping("/scan")
    @Operation(summary = "Scan GitHub repo and store issues")
    public ResponseEntity<GitHubIssueScanResponse> scan(@RequestBody Map<String, String> request) {
        String repo = request.get("repo");
        GitHubIssueScanResponse response = gitHubInsightAIService.scanAndStoreIssues(repo);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/analyze")
    public ResponseEntity<Map> analyze(@RequestBody Map<String, String> request) {
        String repo = request.get("repo");
        String prompt = request.get("prompt");
        String analysis = gitHubInsightAIService.analyzeIssues(repo, prompt);
        return ResponseEntity.ok(Map.of("analysis", analysis));
    }



}
