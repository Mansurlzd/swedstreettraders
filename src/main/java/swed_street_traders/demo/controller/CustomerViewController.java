package swed_street_traders.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import swed_street_traders.demo.service.MarketDataLoader;
import swed_street_traders.demo.service.market.dao.StockData;

import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerViewController {

    @Autowired
    private MarketDataLoader marketDataLoader;

    @GetMapping("")
    public final String getCustomerPage(){
        return "customer";
    }

    @GetMapping("/market/{stockSymbol}")
    public final Map<String, StockData> getMarketData(@PathVariable String stockSymbol){
        return marketDataLoader.getData(stockSymbol);
    }
}
