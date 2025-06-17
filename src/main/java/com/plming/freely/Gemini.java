package com.plming.freely;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Google Gemini AI 모델을 사용하여 자연어 응답을 생성하는 서비스입니다.
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
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    /**
     * 주어진 질의에 대해 Gemini AI 모델을 사용하여 응답을 생성합니다.
     * 사용량 제한으로 생성이 불가할 경우, 다른 Gemini 모델을 시도합니다.
     * 모든 모델이 실패한 경우 null을 반환합니다.
     *
     * @param query 언어 모델에 전달할 쿼리
     * @return ChatResponse 모델의 생성된 응답, 모든 모델이 실패한 경우 null
     */
    public ChatResponse generateOrNull(String query) {
        final String[] MODEL_NAMES = {
                "gemini-2.0-flash",
                "gemini-2.0-flash-lite",
                "gemini-1.5-flash",
                "gemini-1.5-pro",
        };

        for (String modelName : MODEL_NAMES) {
            try {
                return generateWith(modelName, query);
            } catch (RuntimeException _ignored) {
                // intentional fallthrough
            }
        }

        return null;
    }

    /**
     * 특정 Gemini 모델을 사용하여 주어진 질의에 대한 응답을 생성합니다.
     * 요청이 실패하거나, 응답이 null인 경우 예외를 발생시킵니다.
     *
     * @param modelName 사용할 Gemini 모델 이름
     * @param query     언어 모델에 전달할 쿼리
     * @return ChatResponse 해당 모델로 생성된 응답
     */
    private ChatResponse generateWith(String modelName, String query) {
        var body = client.post()
                .uri("/{modelName}:generateContent?key={apiKey}", modelName, apiKey)
                .body(new GeminiRequest(query))
                .retrieve()
                .body(GeminiResponse.class);

        if (body == null) {
            assert (false);
            throw new RuntimeException();
        }

        String text = body.candidates.getFirst().content.parts.getFirst().text;
        return new ChatResponse(text);
    }
}
