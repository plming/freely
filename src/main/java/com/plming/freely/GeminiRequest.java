package com.plming.freely;

import java.util.ArrayList;

public class GeminiRequest {
    public ArrayList<Content> contents;

    public GeminiRequest(String text) {
        contents = new ArrayList<>(1);
        contents.add(new Content(text));
    }

    public static class Content {
        public ArrayList<Part> parts;

        public Content(String text) {
            parts = new ArrayList<>(1);
            parts.add(new Part(text));
        }
    }

    public static class Part {
        public final String text;

        public Part(String text) {
            this.text = text;
        }
    }
}
