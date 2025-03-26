package swed_street_traders.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import swed_street_traders.demo.service.NewsService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/api/news/{stockSymbol}")
    @ResponseBody
    public List<String> getNewsForStock(@PathVariable String stockSymbol) {
        return newsService.getNewsForStock(stockSymbol);
    }
}
