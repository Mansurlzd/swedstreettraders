package swed_street_traders.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import swed_street_traders.demo.service.market.dao.StockData;
import swed_street_traders.demo.service.market.dao.StockResponse;

import java.util.Map;
import java.util.Objects;

@Service
public class MarketDataLoader {
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final RestTemplate restTemplate;

    public MarketDataLoader() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, StockData> getData(String stock) {
        try {
            String uri = UriComponentsBuilder
                    .fromHttpUrl(BASE_URL)
                    .queryParam("function", "TIME_SERIES_DAILY")
                    .queryParam("symbol", stock)
//                    .queryParam("apikey", "Z2VDY68B6AGTEGXK")
                    .queryParam("apikey", "demo")
                    .toUriString();
            return Objects.requireNonNull(restTemplate.getForObject(uri, StockResponse.class)).getTimeSeriesDaily().getDailyData();
        } catch (Exception e) {
            return null;
        }
    }
}
