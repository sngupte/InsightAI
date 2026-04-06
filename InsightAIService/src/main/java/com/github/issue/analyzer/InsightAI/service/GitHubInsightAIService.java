package com.github.issue.analyzer.InsightAI.service;

import com.github.issue.analyzer.InsightAI.model.dto.GitHubIssueScanResponse;

public interface GitHubInsightAIService {

    GitHubIssueScanResponse scanAndStoreIssues(String repo);

    String analyzeIssues(String repo, String prompt);
}
