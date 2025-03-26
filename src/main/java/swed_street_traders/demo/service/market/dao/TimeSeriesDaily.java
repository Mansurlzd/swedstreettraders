package swed_street_traders.demo.service.market.dao;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class TimeSeriesDaily {
    private Map<String, StockData> dailyData = new HashMap<>();

    @JsonAnySetter
    public void setDailyData(String date, StockData data) {
        dailyData.put(date, data);
    }

    public Map<String, StockData> getDailyData() {
        return dailyData;
    }

    @Override
    public String toString() {
        return "TimeSeriesDaily{" +
                "dailyData=" + dailyData +
                '}';
    }
}
