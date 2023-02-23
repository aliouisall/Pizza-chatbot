package com.example.demo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WebhookRequest {
    private String responseId;
    private QueryResult queryResult;

    @Data
    public static class QueryResult{
        private String queryText;
        private String action;
        private Map<String, Object> parameters;
        private List<OutputContext> OutputContexts;
        @Data
        public static class OutputContext{
            private String name;
            private Map<String, Object> parameters;
        }
    }
}
