package com.example.startingpoint;

public class Fact {
    private String title;
    private String summary;
    private String url;
    private String imageUrl;

    public Fact(String title, String summary, String url, String imageUrl) {
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
