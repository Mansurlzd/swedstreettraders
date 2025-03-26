package swed_street_traders.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService {
    
    @Value("${newsapi.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public NewsService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<String> getNewsForStock(String stockSymbol) {
        try {
            LocalDate fromDate = LocalDate.now().minusMonths(1);
            String url = String.format("https://newsapi.org/v2/everything?q=%s&from=%s&sortBy=publishedAt&apiKey=%s",
                    stockSymbol,
                    fromDate.format(DateTimeFormatter.ISO_DATE),
                    apiKey);
            
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            JsonNode articles = root.get("articles");
            
            List<String> descriptions = new ArrayList<>();
            if (articles.isArray()) {
                for (JsonNode article : articles) {
                    if (article.has("description") && !article.get("description").isNull()) {
                        descriptions.add(article.get("description").asText());
                    }
                }
            }
            
            return descriptions;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching news for " + stockSymbol, e);
        }
    }
}
