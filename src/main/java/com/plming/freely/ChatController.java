package com.plming.freely;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    private final Gemini gemini;

    @Autowired
    public ChatController(Gemini gemini) {
        this.gemini = gemini;
    }

    @PostMapping("/chat")
    public ChatResponse chat() {
        return gemini.generate();
    }
}
