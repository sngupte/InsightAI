package com.github.issue.analyzer.InsightAI.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "github_issues",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"github_id", "repo"})
        })
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class GithubIssue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false)
    private Long githubId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "html_url")
    private String htmlUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String repo;

    @Column(nullable = false)
    private String state;
}