package swed_street_traders.demo.service.market.dao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockResponse {
    @JsonProperty("Time Series (Daily)")
    private TimeSeriesDaily timeSeriesDaily;

    public TimeSeriesDaily getTimeSeriesDaily() {
        return timeSeriesDaily;
    }
}
