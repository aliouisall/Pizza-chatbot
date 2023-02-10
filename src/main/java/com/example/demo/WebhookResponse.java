package com.example.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class WebhookResponse {
    private String fullfilmentText;
    private List<FulfillmentMessage> FulfillmentMessages;

    @Data
    public static class FulfillmentMessage{
        private String platform;
        private List<Suggestion> Suggestions;
        @Data
        public static class Suggestion{
            private String title;
        }
        private List<SimpleResponse> SimpleResponses;
        @Data
        public static class SimpleResponse{
            private String textToSpeech;
        }
    }
}
