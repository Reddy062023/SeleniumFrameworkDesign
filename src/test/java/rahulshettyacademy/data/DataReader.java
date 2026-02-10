package rahulshettyacademy.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

    public List<HashMap<String, String>> getJsonDataToMap() throws IOException {

        // Read JSON file to String
        String jsonContent = FileUtils.readFileToString(
                new File(System.getProperty("user.dir") +
                        "/src/test/resources/PurchaseOrder.json"),
                StandardCharsets.UTF_8
        );

        // Convert String → List<HashMap>
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                jsonContent,
                new TypeReference<List<HashMap<String, String>>>() {}
        );
    }
}
