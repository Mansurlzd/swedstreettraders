package swed_street_traders.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import swed_street_traders.demo.model.NewsItem;
import swed_street_traders.demo.service.MarketDataLoader;
import swed_street_traders.demo.service.NewsService;
import swed_street_traders.demo.service.SentimentAnalysisService;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private MarketDataLoader marketDataLoader;

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/")
    public final String getCustomerPage(){
        return "customer";
    }

    @GetMapping("/api/news/{stockSymbol}")
    @ResponseBody
    public List<NewsItem> getNewsForStock(@PathVariable String stockSymbol) {
        return newsService.getNewsForStock(stockSymbol);
    }

    @GetMapping("/api/sentiment")
    @ResponseBody
    public Map<String, String> analyzeSentiment(String text) {
        Map<String, String>  sen = sentimentAnalysisService.analyzeSentiments(List.of(text));
        return sen;
    }

    @GetMapping("/market/{stockSymbol}")
    @ResponseBody
    public final Map<String, String> getMarketData(@PathVariable String stockSymbol){
        return marketDataLoader.getData(stockSymbol);
    }
}
