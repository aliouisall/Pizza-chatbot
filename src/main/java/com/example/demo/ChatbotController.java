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
                                            new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Quel genre de film ?")
                                    )))
                    )
            );
        }
        else {
            webHoRes.setFulfillmentMessages(List.of(
                    new WebhookResponse.FulfillmentMessage()
                            .setPlatform("ACTIONS_ON_GOOGLE")
                            .setCarouselSelect(new WebhookResponse.FulfillmentMessage.CarouselSelect().setItems(List.of(
                                    new WebhookResponse.FulfillmentMessage.Item()
                                            .setTitle("TTTTT")
                                            .setDescription("DDDDD")
                                            .setInfo(new WebhookResponse.FulfillmentMessage.Info().setKey("KKKKK"))
                                            .setImage(new WebhookResponse.FulfillmentMessage.Image().setImageUri("https://image.over-blog.com/Rm53RG4zyBFNdT1-bdZgZoGOYrg=/filters:no_upscale()/image%2F0953084%2F20221026%2Fob_88ffa7_5229-638022957267263454.jpg")),
                                    new WebhookResponse.FulfillmentMessage.Item()
                                            .setTitle("TTTTT")
                                            .setDescription("DDDDD")
                                            .setInfo(new WebhookResponse.FulfillmentMessage.Info().setKey("KKKKK"))
                                            .setImage(new WebhookResponse.FulfillmentMessage.Image().setImageUri("https://i.skyrock.net/4411/47464411/pics/3154505458_1_2_vZZvb2HL.jpg"))
                            ))),
                    new WebhookResponse.FulfillmentMessage()
                            .setPlatform("ACTIONS_ON_GOOGLE")
                            .setSimpleResponses(new WebhookResponse.FulfillmentMessage.SimpleResponses().setSimpleResponses(List.of(
                                    new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Bien voici ce que je te propose :")
                            )))
            ));
        }
        log.info("{}", data.getQueryResult().getAction());
        return webHoRes;
    }
}

// après choix genre -> afficher 3 images de film sous forme de carrousel
// placer bloc set items dans le else après test