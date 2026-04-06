package com.github.issue.analyzer.InsightAI.repository;

import com.github.issue.analyzer.InsightAI.model.GithubIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GithubIssueRepository extends JpaRepository<GithubIssue, Long> {

    Optional<GithubIssue> findByGithubIdAndRepo(Long id, String repo);

    List<GithubIssue> findByRepo(String repo);
}
