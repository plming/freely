package com.plming.freely;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @PostMapping("/chat")
    public ChatResponse chat() {
        return new ChatResponse("Hello World!");
    }
}
