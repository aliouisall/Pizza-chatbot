package com.example.demo;

import lombok.Data;

@Data
public class WebhookRequest {
    private String responseId;

    @Data
    public class QueryResult{
        private String queryText;
        private String action;
    }
}
