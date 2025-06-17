package com.plming.freely;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final Gemini gemini;

    @Autowired
    public ChatController(Gemini gemini) {
        this.gemini = gemini;
    }

    /**
     * 클라이언트로부터 받은 쿼리를 생성형 AI를 통해 응답을 생성합니다.
     *
     * @param chatRequest 클라이언트로부터 받은 요청 객체
     * @return 생성된 응답 또는 서비스 불가 상태 코드
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> createChat(@RequestBody ChatRequest chatRequest) {
        String query = chatRequest.getQuery();

        ChatResponse responseOrNull = gemini.generateOrNull(query);
        if (responseOrNull == null) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        return ResponseEntity.ok(responseOrNull);
    }
}
