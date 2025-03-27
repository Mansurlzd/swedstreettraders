package swed_street_traders.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import swed_street_traders.demo.service.market.dao.StockData;
import swed_street_traders.demo.service.market.dao.StockResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MarketDataLoader {
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final RestTemplate restTemplate;

    public MarketDataLoader() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, String> getData(String stock) {
        try {
            String uri = UriComponentsBuilder
                    .fromHttpUrl(BASE_URL)
                    .queryParam("function", "TIME_SERIES_DAILY")
                    .queryParam("symbol", stock)
                    .queryParam("apikey", "Z2VDY68B6AGTEGXK")
                    .toUriString();

            StockResponse response = restTemplate.getForObject(uri, StockResponse.class);
            if (response != null && response.getTimeSeriesDaily() != null) {
                Map<String, String> openValues = new LinkedHashMap<>();
                response.getTimeSeriesDaily().getDailyData().forEach((date, stockDetails) -> {
                    openValues.put(date, stockDetails.getClose());
                });
                return openValues;
            }
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
