package swed_street_traders.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
public class MarketDataLoader {
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final RestTemplate restTemplate;

    public MarketDataLoader() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getData(String stock) {
        try {
            String uri = UriComponentsBuilder
                    .fromHttpUrl(BASE_URL)
                    .queryParam("function", "GLOBAL_QUOTE")
                    .queryParam("symbol", stock)
                    .queryParam("apikey", "Z2VDY68B6AGTEGXK")
                    .toUriString();

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            if (response != null && response.containsKey("Global Quote")) {
                @SuppressWarnings("unchecked")
                Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
                
                // Create a structured response
                Map<String, Object> result = new HashMap<>();
                
                // Price data
                Map<String, Double> priceData = new HashMap<>();
                priceData.put("current", Double.parseDouble(quote.get("05. price")));
                priceData.put("previous", Double.parseDouble(quote.get("08. previous close")));
                result.put("price", priceData);
                
                // Volume data
                Map<String, Long> volumeData = new HashMap<>();
                volumeData.put("current", Long.parseLong(quote.get("06. volume")));
                // For demo, set previous volume as 90% of current
                volumeData.put("previous", (long)(Long.parseLong(quote.get("06. volume")) * 0.9));
                result.put("volume", volumeData);
                
                return result;
            }
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
