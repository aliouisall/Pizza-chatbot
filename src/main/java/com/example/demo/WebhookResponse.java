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
        private Suggestions Suggestions;

        @Data
        public static class Suggestions{
            private List<Suggestion> suggestions;
        }
        @Data
        public static class Suggestion{
            private String title;
        }
        private SimpleResponses SimpleResponses;
        @Data
        public static class SimpleResponses{
            private List<SimpleResponse> simpleResponses;
        }
        @Data
        public static class SimpleResponse{
            private String textToSpeech;
        }
    }
}