package com.github.issue.analyzer.InsightAI.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // ignore extra fields
public class GithubIssueDTO {

    private Long id;

    private String title;

    private String body;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    private String state;

}