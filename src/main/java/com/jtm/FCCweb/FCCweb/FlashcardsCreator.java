package com.jtm.FCCweb.FCCweb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang.StringEscapeUtils.unescapeXml;


public class FlashcardsCreator {
    JSONmodel jsonModel = new JSONmodel();
    WebsiteProvider websiteProvider = new WebsiteProvider();


    String getJsonTranslationContent(GlosbeAPItranslationModel apiParameters, WebsiteProvider websiteProvider) {
        /*  To get data visit /gapi/{functionName}[?[{functionParameter1}={value}
             [&{functionParameter2}={value}[&{functionParameter3}={value}...]]]] page.
             Example: Translate Polish 'witaj' into English, output format is json:
             https://glosbe.com/gapi/translate?from=pol&dest=eng&format=json&phrase=witaj&pretty=true */

        String jsonURL = "https://www.glosbe.com/gapi/translate?from=" + apiParameters.getFrom() + "&dest=" +
                apiParameters.getDest() + "&format=json&phrase=" + apiParameters.getPhraseToTranslate() + "&pretty=true";
        System.out.println("\ntranslation URL: " + jsonURL + "\n"); // only for debugging
        return websiteProvider.getUrlContents(jsonURL);
    }

    String getJsonExampleContent(GlosbeAPItranslationModel apiParameters, WebsiteProvider websiteProvider) {
        String jsonURL = "https://glosbe.com/gapi/tm?from=" + apiParameters.getFrom() + "&dest=" + apiParameters.getDest()
                + "&format=json&phrase=" + apiParameters.getPhraseToTranslate() + "&page=1&pretty=true";
        System.out.println("\nexample URL: " + jsonURL + "\n"); // only for debugging
        return websiteProvider.getUrlContents(jsonURL);

    }

    void printFlashcards(GlosbeAPItranslationModel apiParameters, CommunicationWithUser communicationWithUser, WebsiteProvider websiteProvider) {

        while (true) {
                getDataFromJSON(communicationWithUser, apiParameters);
                System.out.println("\n------------------------------------\n\n FRONT SIDE:\n");
                System.out.println("\t" + apiParameters.getPhraseToTranslate()); // print source phrase

                //todo: szybsze rozwiązanie: https://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-java-map
                Set set = getExamples(apiParameters, websiteProvider).entrySet();
                Iterator iterator = set.iterator();
                while(iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry)iterator.next();
                    System.out.println("\nexample: " + mentry.getKey());
                }
                
                System.out.println("\n\n BACK SIDE:\n\n");
                System.out.println("\t" + jsonModel.getTranslation()); //print translation

                if (!(apiParameters.getFrom().equals("pol"))) {
                    System.out.println("\nmeaning: " + jsonModel.getMeaning()); //print meaning
                }

                iterator = set.iterator();
                while(iterator.hasNext()) {
                    Map.Entry mentry = (Map.Entry)iterator.next();
                    System.out.println("\nexample: " + mentry.getValue()); //print example

                }




            System.out.println("\n----------------------------------");

        }

    }
    void getDataFromJSON(CommunicationWithUser communicationWithUser, GlosbeAPItranslationModel apiParameters) {
        communicationWithUser.userSetPhraseToTranslate();
        ObjectMapper translationMapper = new ObjectMapper();

        // Don't throw an exception when json has extra fields you are
        // not serializing on. This is useful when you want to use a pojo
        // for deserialization and only care about a portion of the json
        translationMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        try {

            JsonNode translationRoot = translationMapper.readTree(getJsonTranslationContent(apiParameters, websiteProvider));

            JsonNode tuc = translationRoot.get("tuc"); // get() calls specific value in the array /tuc/0/meanings/0/text
            JsonNode phrase = tuc.get(0).get("phrase");
            jsonModel.setTranslation(phrase.get("text").asText());
            int enMeaningIndex = 0;
            //for (enMeaningIndex = 0; !(tuc.get(0).get("meanings").get(enMeaningIndex).get("language").asText().equals("en")); enMeaningIndex++) {
            //   System.out.println("loop pass" + enMeaningIndex);
            //}
            jsonModel.setMeaning(unescapeXml(tuc.get(0).get("meanings").get(enMeaningIndex).get("text").asText())); // unescapeXml skips entities like '&quot'


        }
        catch (IOException e) {
        e.printStackTrace();
    }
    }

    Map<String, String> getExamples(GlosbeAPItranslationModel apiParameters, WebsiteProvider websiteProvider) {
        Map<String, String> examples = new HashMap();

        ObjectMapper exampleMapper = new ObjectMapper();
        exampleMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        //Map.Entry<String, String> example;
//        long i = 0;
//        Iterator<Map.Entry<String, String>> it = examples.entrySet().iterator();
//        while (it.hasNext() && it < 3) {
//            Map.Entry<String, String> pair = it.next();
//            i += pair.getKey() + pair.getValue();
//        }

        try {
            JsonNode exampleRoot = exampleMapper.readTree(getJsonExampleContent(apiParameters, websiteProvider));
            for (int i = 0; i < 3; i++) {
                examples.put(exampleRoot.get("examples").get(i).get("first").asText(), exampleRoot.get("examples").get(i).get("second").asText());
            }

        }
        catch (IOException e) { e.printStackTrace(); }

        return examples;
    }

}
