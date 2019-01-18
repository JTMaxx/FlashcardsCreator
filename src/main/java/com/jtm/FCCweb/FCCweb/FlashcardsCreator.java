package com.jtm.FCCweb.FCCweb;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtm.FCCweb.FCCweb.model.FCmaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang.StringEscapeUtils.unescapeXml;


public class FlashcardsCreator {
    WebsiteProvider websiteProvider = new WebsiteProvider();
    FCmaker fcMaker = new FCmaker();


    String getJsonTranslationContent(FCmaker fcMaker, WebsiteProvider websiteProvider) {
        /*  To get data visit /gapi/{functionName}[?[{functionParameter1}={value}
             [&{functionParameter2}={value}[&{functionParameter3}={value}...]]]] page.
             Example: Translate Polish 'witaj' into English, output format is json:
             https://glosbe.com/gapi/translate?from=pol&dest=eng&format=json&phrase=witaj&pretty=true */

        String jsonURL = "https://www.glosbe.com/gapi/translate?from=" + fcMaker.getFromLanguage() + "&dest=" +
                fcMaker.getDestLanguage() + "&format=json&phrase=" + fcMaker.getPhraseToTranslate() + "&pretty=true";
        System.out.println("\ntranslation URL: " + jsonURL + "\n"); // only for debugging
        return websiteProvider.getUrlContents(jsonURL);
    }

    String getJsonExampleContent(FCmaker fcMaker, WebsiteProvider websiteProvider) {
        String jsonURL = "https://glosbe.com/gapi/tm?from=" + fcMaker.getFromLanguage() + "&dest=" + fcMaker.getDestLanguage()
                + "&format=json&phrase=" + fcMaker.getPhraseToTranslate() + "&page=1&pretty=true";
        System.out.println("\nexample URL: " + jsonURL + "\n"); // only for debugging
        return websiteProvider.getUrlContents(jsonURL);

    }
    void saveFlashcards(FCmaker fcMaker, WebsiteProvider websiteProvider) {
        getDataFromJSON(fcMaker);

        //todo: szybsze rozwiązanie: https://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-java-map
//        Set set = getExamples(fcMaker, websiteProvider).entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()) {
//            Map.Entry mentry = (Map.Entry)iterator.next();
//            fcMaker.addExample(mentry.getKey(), mentry.getValue());
//        }
    }

//    void printFlashcards(GlosbeAPItranslationModel glosbeAPItranslationModel, WebsiteProvider websiteProvider) {
//
//        while (true) {
//            //while (fcMaker.getPhraseToTranslate() == null) {}
//                getDataFromJSON(glosbeAPItranslationModel);
//                System.out.println("\n------------------------------------\n\n FRONT SIDE:\n");
//                System.out.println("\t" + fcMaker.getPhraseToTranslate()); // print source phrase
//
//                //todo: szybsze rozwiązanie: https://stackoverflow.com/questions/46898/how-to-efficiently-iterate-over-each-entry-in-a-java-map
//                Set set = getExamples(glosbeAPItranslationModel, websiteProvider).entrySet();
//                Iterator iterator = set.iterator();
//                while(iterator.hasNext()) {
//                    Map.Entry mentry = (Map.Entry)iterator.next();
//                    System.out.println("\nexample: " + mentry.getKey());
//                }
//
//                System.out.println("\n\n BACK SIDE:\n\n");
//                System.out.println("\t" + fcMaker.getTranslation()); //print translation
//
//                if (!(glosbeAPItranslationModel.getFrom().equals("pol"))) {
//                    System.out.println("\nmeaning: " + fcMaker.getMeaning()); //print meaning
//                }
//
//                iterator = set.iterator();
//                while(iterator.hasNext()) {
//                    Map.Entry mentry = (Map.Entry)iterator.next();
//                    System.out.println("\nexample: " + mentry.getValue()); //print example
//
//                }
//
//
//
//
//            System.out.println("\n----------------------------------");
//
//        }
//
//    }
  public void getDataFromJSON(FCmaker fcMaker) {

        //fcMaker.getPhraseToTranslate(); //todo: tu było wcześniej getPhraseToTranslate from communicationWithUser, co użyć teraz w zamian?
        ObjectMapper translationMapper = new ObjectMapper();

        // Don't throw an exception when json has extra fields you are
        // not serializing on. This is useful when you want to use a pojo
        // for deserialization and only care about a portion of the json
        translationMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


        try {

            JsonNode translationRoot = translationMapper.readTree(getJsonTranslationContent(fcMaker, websiteProvider));

            JsonNode tuc = translationRoot.get("tuc"); // get() calls specific value in the array /tuc/0/meanings/0/text
            JsonNode phrase = tuc.get(0).get("phrase");
            fcMaker.setTranslation(phrase.get("text").asText());
            int enMeaningIndex = 0;
            //for (enMeaningIndex = 0; !(tuc.get(0).get("meanings").get(enMeaningIndex).get("language").asText().equals("en")); enMeaningIndex++) {
            //   System.out.println("loop pass" + enMeaningIndex);
            //}
            fcMaker.setMeaning(unescapeXml(tuc.get(0).get("meanings").get(enMeaningIndex).get("text").asText())); // unescapeXml skips entities like '&quot'


        }
        catch (IOException e) {
        e.printStackTrace();
    }
    }

    Map<String, String> getExamples(FCmaker fcMaker, WebsiteProvider websiteProvider) {
        Map<String, String> examples = new HashMap();

        ObjectMapper exampleMapper = new ObjectMapper();
        exampleMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            JsonNode exampleRoot = exampleMapper.readTree(getJsonExampleContent(fcMaker, websiteProvider));
            for (int i = 0; i < 3; i++) {
                examples.put(exampleRoot.get("examples").get(i).get("first").asText(), exampleRoot.get("examples").get(i).get("second").asText());
            }

        }
        catch (IOException e) { e.printStackTrace(); }

        return examples;
    }

}
