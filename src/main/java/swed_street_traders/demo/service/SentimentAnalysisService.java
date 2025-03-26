package swed_street_traders.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    @Value("${text2data.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SentimentAnalysisService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, String> analyzeSentiments(List<String> texts) {
        Map<String, String> results = new HashMap<>();
        for (String text : texts) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + apiKey);

                HttpEntity<String> request = new HttpEntity<>(String.format("{\"inputs\": \"%s\"}", text), headers);
                String response = restTemplate.postForObject(apiUrl, request, String.class);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootArray = objectMapper.readTree(response);

                JsonNode innerArray = rootArray.get(0);
                String bestLabel = "";
                double maxScore = Double.MIN_VALUE;

                // Find the highest score
                for (JsonNode node : innerArray) {
                    String label = node.get("label").asText();
                    double score = node.get("score").asDouble();

                    if (score > maxScore) {
                        maxScore = score;
                        bestLabel = label;
                    }
                }

                results.put(text, String.format("%s (%.2f)", bestLabel, maxScore));
            } catch (Exception e) {
                e.printStackTrace();
                results.put(text, "NEUTRAL (0.00)");
            }
        }
        return results;
    }
}
