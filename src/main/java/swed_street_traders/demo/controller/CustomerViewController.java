package swed_street_traders.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swed_street_traders.demo.service.MarketDataLoader;

import java.util.Map;

@Controller
public class CustomerViewController {

    @Autowired
    private MarketDataLoader marketDataLoader;

    @GetMapping("/customer")
    public final String getCustomerPage(){
        return "customer";
    }

    @GetMapping("/market/{stockSymbol}")
    @ResponseBody
    public final Map<String, String> getMarketData(@PathVariable String stockSymbol){
        return marketDataLoader.getData(stockSymbol);
    }
}
