package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ChatbotController {
    @PostMapping("/webhook")
    public WebhookResponse webhook(@RequestBody WebhookRequest data){
        RestTemplate restTemplate = new RestTemplate();
        String tmdbResourceUrl = "https://api.themoviedb.org/3/search/movie?api_key=7996ca1b1e575167242fa0d3ea6f5ef1";
        Map <String, List<String>> movies = new HashMap<>();
        movies.put("Action", List.of("Aquaman", "Uncharted"));
        movies.put("Comédie", List.of("Ted", "Paddington"));
        log.info("{}", movies.keySet());
        log.info("{}", data);
        WebhookResponse webHoRes = new WebhookResponse();
        log.info("{}", webHoRes);
        log.info("{}", data.getQueryResult().getParameters());
        log.info("{}", data.getQueryResult().getOutputContexts());
        String outputContext;
        Integer option = 0;
        var genre = (String)data.getQueryResult().getParameters().get("Genre");
        if (data.getQueryResult().getAction().equals("movieSLAction")){
            for (int i=0; i<data.getQueryResult().getOutputContexts().size(); i++){
                if (data.getQueryResult().getOutputContexts().get(i).getName().endsWith("actions_intent_option"))
                    option = (Integer)data.getQueryResult().getOutputContexts().get(i).getParameters().get("OPTION");
            }
            log.info("{}", option);
            Movie movieSelectedResponse = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + option +"?api_key=7996ca1b1e575167242fa0d3ea6f5ef1", Movie.class);
            webHoRes.setFulfillmentMessages(List.of(
                    new WebhookResponse.FulfillmentMessage()
                            .setBasicCard(new WebhookResponse.FulfillmentMessage.BasicCard()
                                    .setImageURI("https://image.tmdb.org/t/p/w500" + movieSelectedResponse.getPosterPath())
                                    //.setButtons(List.of(new WebhookResponse.FulfillmentMessage.Button().setOpenUriAction(new WebhookResponse.FulfillmentMessage.OpenUriAction().setUri("UriAction"))))
                            )
            ));
        }
        else if (genre == null || genre.isEmpty()){
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
            List<WebhookResponse.FulfillmentMessage.Item> items = new ArrayList<>();
            for(String movie:movies.get(genre)){
                MovieDBResponse genreSelectedResponse = restTemplate.getForObject(tmdbResourceUrl + "&query=" + movie + "&language=fr", MovieDBResponse.class);
                WebhookResponse.FulfillmentMessage.Item item = new WebhookResponse.FulfillmentMessage.Item()
                        .setTitle(genreSelectedResponse.getResults().get(0).getTitle() + "33")
                        .setDescription(genreSelectedResponse.getResults().get(0).getOverview())
                        .setInfo(new WebhookResponse.FulfillmentMessage.Info().setKey(genreSelectedResponse.getResults().get(0).getId().toString()))
                        .setImage(new WebhookResponse.FulfillmentMessage.Image().setImageUri("https://image.tmdb.org/t/p/w500" + genreSelectedResponse.getResults().get(0).getPosterPath()));
                items.add(item);
            }
            webHoRes.setFulfillmentMessages(List.of(
                    new WebhookResponse.FulfillmentMessage()
                            .setPlatform("ACTIONS_ON_GOOGLE")
                            .setSimpleResponses(new WebhookResponse.FulfillmentMessage.SimpleResponses().setSimpleResponses(List.of(
                                    new WebhookResponse.FulfillmentMessage.SimpleResponse().setTextToSpeech("Bien voici ce que je te propose :")
                    ))),
                    new WebhookResponse.FulfillmentMessage()
                            .setPlatform("ACTIONS_ON_GOOGLE")
                            .setCarouselSelect(new WebhookResponse.FulfillmentMessage.CarouselSelect().setItems(items))
            ));
        }
        return webHoRes;
    }
}

// boucler sur les outputcontext et vérifier si name se termine par actions intent option
// dans le cas où c'est vrai récupérer la valeur du champ option qui correspond à l'id du film choisi