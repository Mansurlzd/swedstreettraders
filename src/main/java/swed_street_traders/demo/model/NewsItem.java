package swed_street_traders.demo.model;

public class NewsItem {
    private String description;
    private String sentiment;
    private String publishedAt;

    public NewsItem(String description, String sentiment, String publishedAt) {
        this.description = description;
        this.sentiment = sentiment;
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
