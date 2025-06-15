package com.plming.freely;

public class ChatRequest {
    private final String query;

    public ChatRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
