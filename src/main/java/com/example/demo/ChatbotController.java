package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class ChatbotController {

    @PostMapping("/webhook")
    public WebhookResponse webhook(@RequestBody WebhookRequest data){
        log.info("{}", data);
        WebhookResponse webHoRes = new WebhookResponse();
        log.info("{}", webHoRes);
        log.info("{}", data.getQueryResult().getParameters());
        var genre = (String)data.getQueryResult().getParameters().get("Genre");
        if (genre == null || genre.isEmpty()){
            webHoRes.setFullfilmentText("Quel genre de film ?");
            webHoRes.setFulfillmentMessages(List.of(
                            new WebhookResponse.FulfillmentMessage()
                                    .setPlatform("ACTIONS_ON_GOOGLE")
                                    .setSuggestions(new WebhookResponse.FulfillmentMessage.Suggestions()
                                            .setSuggestions(List.of(
                                                    new WebhookResponse.FulfillmentMessage.Suggestion().setTitle("Action"),
                                                    new WebhookResponse.FulfillmentMessage.Suggestion().setTitle("Aventure"),
                                                    new WebhookResponse.FulfillmentMessage.Suggestion().setTitle("Comédie")
                                            ))
                                    ),
                            new WebhookResponse.FulfillmentMessage()
                                    .setPlatform("ACTIONS_ON_GOOGLE")
                                    .setSimpleResponses(new WebhookResponse.FulfillmentMessage.SimpleResponses().setSimpleResponses(List.of(
                                            new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Je n'ai pas bien compris.")
                                    )))
                    )
            );
        }
        else {
            webHoRes.setFullfilmentText("Vous voulez voir un film de " + genre + ". Bien.");
        }
        log.info("{}", data.getQueryResult().getAction());
        return webHoRes;
    }
}

// match intent basé sur action and parameters