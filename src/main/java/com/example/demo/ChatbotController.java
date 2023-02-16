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
        }
        else{
            webHoRes.setFullfilmentText("Vous voulez voir un film de " + genre + ". Bien.");
        }
        webHoRes.setFulfillmentMessages(List.of(
                new WebhookResponse.FulfillmentMessage()
                        .setPlatform("ACTIONS_ON_GOOGLE")
                        .setSuggestions(new WebhookResponse.FulfillmentMessage.Suggestions().setSuggestions(List.of(
                                new WebhookResponse.FulfillmentMessage.Suggestion().setTitle("Action")
                        )))
                        .setSimpleResponses(new WebhookResponse.FulfillmentMessage.SimpleResponses().setSimpleResponses(List.of(
                                new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Test")
                        )))
                )
        );
        return webHoRes;
    }
}

// class ffmes
// class sugg contient list de suggestion ayant title comme propriété; même chose pour simple response
// objet simplePerson qui englobe une liste