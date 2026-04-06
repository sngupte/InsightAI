package com.github.issue.analyzer.InsightAI.client;

import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LlmClient {

    private final ChatModel chatModel;

    public LlmClient(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String analyzeIssueWithLLM(String systemPrompt, String userPrompt) {
        ChatResponse chatResponse = chatModel.call( new Prompt(
                List.of(
                        new SystemMessage(systemPrompt),
                        new UserMessage(userPrompt)
                ),
                AnthropicChatOptions.builder()
                        .model("claude-haiku-4-5")
                        .maxTokens(1024)
                        .build()
        ));
       return  chatResponse.getResult().getOutput().getText();
    }


}
