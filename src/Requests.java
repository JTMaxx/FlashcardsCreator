import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Scanner;
import java.lang.String;

import static org.apache.commons.lang.StringEscapeUtils.unescapeXml;

public class Requests {


    public static void main(String[] args) {
        APIparameters apiParameters = new APIparameters();
        CommunicationWithUser communicationWithUser = new CommunicationWithUser();

        communicationWithUser.setTranslationWay(apiParameters);

        while (true) {
            Scanner scanApiParam = new Scanner(System.in); //todo: make it global
            System.out.println("Put phrase to translate");

            //todo: it should use apiParameters created in CommunicationWithUser
            apiParameters.setPhraseToTranslate(scanApiParam.next()); //error at reading value from scanner if other translations
            //scanApiParam.close();

            GetContent getContent = new GetContent();

        /*  To get data visit /gapi/{functionName}[?[{functionParameter1}={value}
         [&{functionParameter2}={value}[&{functionParameter3}={value}...]]]] page.
         Example: Translate Polish 'witaj' into English, output format is json:
         https://glosbe.com/gapi/translate?from=pol&dest=eng&format=json&phrase=witaj&pretty=true */

            String jsonURL = "https://www.glosbe.com/gapi/translate?from=" + apiParameters.getFrom() + "&dest=" +
                    apiParameters.getDest() + "&format=json&phrase=" + apiParameters.getPhraseToTranslate() + "&pretty=true";
            String jsonContent = getContent.getUrlContents(jsonURL);
            System.out.println("\nURL: " + jsonURL + "\n");

            ObjectMapper translationMapper = new ObjectMapper();
            translationMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try {

                JsonNode root = translationMapper.readTree(jsonContent);

                JsonNode tuc = root.get("tuc"); // get() calls specific value in the array /tuc/0/meanings/0/text
                JsonNode phrase = tuc.get(0).get("phrase");
                String textPhrase = phrase.get("text").asText();

                System.out.println("\n------------------------------------\n\n FRONT SIDE:\n");
                System.out.println("\t" + apiParameters.getPhraseToTranslate()); // print source phrase
                System.out.println("\n\n BACK SIDE:\n\n");
                System.out.println("\t" + textPhrase); //print translation


                //for (enMeaningIndex = 0; !(tuc.get(0).get("meanings").get(enMeaningIndex).get("language").asText().equals("en")); enMeaningIndex++) {
                 //   System.out.println("loop pass" + enMeaningIndex);
                //}

                int enMeaningIndex = 0;
                //If translating phrase is not in polish, print meaning of this phrase
                if (!(apiParameters.getFrom().equals("pol"))) {
                    //todo: It sometimes prints meaning in polish e.g. choosing eng->pol and typing 'digit'.
                    String meaningsText = unescapeXml(tuc.get(0).get("meanings").get(enMeaningIndex).get("text").asText()); // skips entities like '&quot'
                    System.out.println("meaning: " + meaningsText);
                }


                System.out.println("\n----------------------------------");


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}


