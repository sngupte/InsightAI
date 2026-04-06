package com.github.issue.analyzer.InsightAI.config;

import com.github.issue.analyzer.InsightAI.client.GitHubClient;
import com.github.issue.analyzer.InsightAI.client.LlmClient;
import com.github.issue.analyzer.InsightAI.repository.GithubIssueRepository;
import com.github.issue.analyzer.InsightAI.service.GitHubInsightAIService;
import com.github.issue.analyzer.InsightAI.service.GitHubInsightAIServiceImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class GitHubInsightAIConfig {

    @Autowired
    private Environment env;

    @Bean("githubWebClient")
    public WebClient githubWebClient() {
        return WebClient.builder()
                .baseUrl(env.getProperty("github.api.base-url"))
                .build();
    }




    @Bean
    GitHubClient gitHubClient(@Qualifier("githubWebClient") WebClient webClient) {
        return new GitHubClient(webClient);
    }

    @Bean
    LlmClient llmClient(ChatModel chatModel) {
        return new LlmClient(chatModel);
    }

    @Bean
    public GitHubInsightAIService gitHubInsightAIService(GitHubClient gitHubClient, LlmClient llmClient, GithubIssueRepository githubIssueRepository) {
        return new GitHubInsightAIServiceImpl(githubIssueRepository, gitHubClient, llmClient);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GitHub Issue Analyzer API")
                        .version("1.0")
                        .description("APIs for scanning and analyzing GitHub issues"));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}
