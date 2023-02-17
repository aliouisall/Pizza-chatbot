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
        private SimpleResponses SimpleResponses;
        private CarouselSelect CarouselSelect;
        private Card Card;

        @Data
        public static class Suggestions{
            private List<Suggestion> suggestions;
        }
        @Data
        public static class Suggestion{
            private String title;
        }
        @Data
        public static class SimpleResponses{
            private List<SimpleResponse> simpleResponses;
        }
        @Data
        public static class SimpleResponse{
            private String textToSpeech;
        }

        @Data
        public static class CarouselSelect{
            private List<Item> items;
        }
        @Data
        public static class Item{
            private String title;
            private String description;
            private Info Info;
            private Image Image;
        }
        @Data
        public static class Info{
            private String key;
        }
        @Data
        public static class Image{
            private String imageUri;
        }
        @Data
        public static class Card{
            private String title;
            private String imageURI;
            private List<Button> Buttons;
        }
        @Data
        public static class Button{
            private String text;
            private String postback;
        }
    }
}

// Ajout des variables image et info dans la classe Item