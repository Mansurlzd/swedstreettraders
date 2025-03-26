package swed_street_traders.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import swed_street_traders.demo.model.NewsItem;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class NewsService {
    
    @Value("${newsapi.key}")
    private String apiKey;
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;
    
    public NewsService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public List<NewsItem> getNewsForStock(String stockSymbol) {
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
                int count = 0;
                for (JsonNode article : articles) {
                    if (count >= 5) break;
                    if (article.has("description") && !article.get("description").isNull()) {
                        String description = article.get("description").asText();
                        descriptions.add(description);
                        count++;
                    }
                }
            }
            
            Map<String, String> sentiments = sentimentAnalysisService.analyzeSentiments(descriptions);
            List<NewsItem> newsItems = new ArrayList<>();
            int index = 0;
            for (JsonNode article : articles) {
                if (index >= descriptions.size()) break;
                String description = article.get("description").asText();
                String publishedAt = article.get("publishedAt").asText();
                newsItems.add(new NewsItem(description, sentiments.get(description), publishedAt));
                index++;
            }
            
            return newsItems;
        } catch (Exception e) {
            throw new RuntimeException("Error fetching news for " + stockSymbol, e);
        }
    }
}
