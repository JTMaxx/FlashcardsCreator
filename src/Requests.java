import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

import java.util.Scanner;
import org.apache.commons.lang.StringEscapeUtils;
import java.lang.Object;

import java.lang.String;

import static org.apache.commons.lang.StringEscapeUtils.unescapeXml;

public class Requests {


    public static void main(String[] args) {
        int enMeaningIndex;
        APIparameters apiParameters = new APIparameters();

        Scanner scanApiParam = new Scanner(System.in);
        System.out.println("polish -> english\nchoose 1\nenglish->polish\nchoose 2\nother translation\nchoose 3");
        int transWayChoice = scanApiParam.nextInt();

        if (transWayChoice == 1) {
            apiParameters.setFrom("pol");
            apiParameters.setDest("eng");
        }
        else if (transWayChoice == 2) {

            apiParameters.setFrom("eng");
            apiParameters.setDest("pol");
        }
        //czy tu nie wystarczy jeden obiekt klasy Scanner?
        else if (transWayChoice == 3) {
            Scanner scanOtherTrans = new Scanner(System.in);
            System.out.println("Use shortcuts like 'pol', 'fra', 'deu'\nPut language which you want translate from");
            apiParameters.setFrom(scanOtherTrans.nextLine());
            System.out.println("Put destination language");
            apiParameters.setDest(scanOtherTrans.nextLine());
            //scanOtherTrans.close(); // Why does it cause also closing of scanApiParam? Because it actually both close underlying stream 'System.in'
        }

        while (true) {
            System.out.println("Put phrase to translate");
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

                System.out.println("\n------------------------------------\n\n\tFRONT:\n\n" + apiParameters.getPhraseToTranslate());
                System.out.println("\n\n\tBACK:\n\n");
                System.out.println("translation: " + textPhrase);


                for (enMeaningIndex = 0; !(tuc.get(0).get("meanings").get(enMeaningIndex).get("language").asText().equals("en")); enMeaningIndex++) {
                    System.out.println("loop pass" + enMeaningIndex);
                }
                    String meaningsText = unescapeXml(tuc.get(0).get("meanings").get(enMeaningIndex).get("text").asText()); // skips entities like '&quot'
                    System.out.println("meaning: " + meaningsText);


                System.out.println("\n----------------------------------");


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}


