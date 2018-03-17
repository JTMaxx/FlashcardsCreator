import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import com.fasterxml.jackson.databind.JsonNode;

public class ExampleStructure {
    private static ObjectMapper mapper = new ObjectMapper();
    static JsonNode getExampleRoot() throws IOException {
        InputStream exampleInput =
                ExampleStructure.class.getClassLoader().
                        getResourceAsStream("https://glosbe.com/gapi/translate?from=pol&dest=eng&format=json&phrase=witaj&pretty=true");

        JsonNode rootNode = mapper.readTree(exampleInput);

        JsonNode locatedNode = rootNode.path("tuc").path("0").path("phrase").path("text");
        return locatedNode;
    }
}