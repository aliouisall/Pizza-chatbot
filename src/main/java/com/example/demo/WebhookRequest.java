package com.example.demo;

import lombok.Data;

import java.util.Map;

@Data
public class WebhookRequest {
    private String responseId;
    private QueryResult queryResult;

    @Data
    public class QueryResult{
        private String queryText;
        private String action;
        private Map<String, Object> parameters;
    }
}
