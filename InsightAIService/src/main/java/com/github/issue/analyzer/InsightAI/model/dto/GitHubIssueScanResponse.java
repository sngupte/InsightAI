package com.github.issue.analyzer.InsightAI.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public record GitHubIssueScanResponse(
        String repo,
        int issuesFetched,
        String storedStatus
) {
}
