package com.plming.freely;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Gemini AI 서비스
 *
 * @see <a href="https://ai.google.dev/api/generate-content?_gl=1*11mrc69*_up*MQ..*_ga*MjUyNTMxNDI0LjE3NDk5NjA2OTk.*_ga_P1DBVKWT6V*czE3NDk5NjA2OTkkbzEkZzAkdDE3NDk5NjA2OTkkajYwJGwwJGgxNjYxMTc2MTcz#method:-models.generatecontent">Google AI Reference</a>
 */
@Service
public class Gemini {
    private final RestClient client;

    @Value("${gemini.api-key}")
    private String apiKey;

    public Gemini() {
        client = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public ChatResponse generate(String query) {
        var result = client.post()
                .uri("/models/gemini-2.0-flash:generateContent?key={apiKey}", apiKey)
                .body(new GeminiRequest(query))
                .retrieve()
                .body(GeminiResponse.class);

        String text = result.candidates.getFirst().content.parts.getFirst().text;
        return new ChatResponse(text);
    }
}
