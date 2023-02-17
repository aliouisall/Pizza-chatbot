package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class ChatbotController {
    @PostMapping("/webhook")
    public WebhookResponse webhook(@RequestBody WebhookRequest data) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String tmdbResourceUrl = "https://api.themoviedb.org/3/search/movie?api_key=7996ca1b1e575167242fa0d3ea6f5ef1";
        MovieDBResponse response = restTemplate.getForObject(tmdbResourceUrl + "&query=Jack+Reacher&language=fr", MovieDBResponse.class);
        log.info("{}", response.getResults().get(0).getTitle());
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
                            .setSimpleResponses(new WebhookResponse.FulfillmentMessage.SimpleResponses().setSimpleResponses(List.of(
                                    new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Bien voici ce que je te propose :")
                    ))),
                    new WebhookResponse.FulfillmentMessage()
                            .setPlatform("ACTIONS_ON_GOOGLE")
                            .setCarouselSelect(new WebhookResponse.FulfillmentMessage.CarouselSelect().setItems(List.of(
                                    new WebhookResponse.FulfillmentMessage.Item()
                                            .setTitle(response.getResults().get(0).getTitle())
                                            .setDescription(response.getResults().get(0).getOverview())
                                            .setInfo(new WebhookResponse.FulfillmentMessage.Info().setKey(response.getResults().get(0).getId().toString()))
                                            .setImage(new WebhookResponse.FulfillmentMessage.Image().setImageUri("https://image.tmdb.org/t/p/w500" + response.getResults().get(0).getPosterPath())),
                                    new WebhookResponse.FulfillmentMessage.Item()
                                            .setTitle(response.getResults().get(1).getTitle())
                                            .setDescription(response.getResults().get(1).getOverview())
                                            .setInfo(new WebhookResponse.FulfillmentMessage.Info().setKey(response.getResults().get(1).getId().toString()))
                                            .setImage(new WebhookResponse.FulfillmentMessage.Image().setImageUri("https://image.tmdb.org/t/p/w500" + response.getResults().get(1).getPosterPath()))
                    )))
            ));
        }
        log.info("{}", data.getQueryResult().getAction());
        return webHoRes;
    }
}

// après choix genre -> afficher 3 images de film sous forme de carrousel
// placer bloc set items dans le else après test