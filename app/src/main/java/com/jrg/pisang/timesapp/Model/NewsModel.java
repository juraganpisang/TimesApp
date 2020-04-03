package com.jrg.pisang.timesapp.Model;

public class NewsModel {

    String captionNews, sourceNews, timeNews;

    public NewsModel(String captionNews, String sourceNews, String timeNews) {
        this.captionNews = captionNews;
        this.sourceNews = sourceNews;
        this.timeNews = timeNews;
    }

    public String getCaptionNews() {
        return captionNews;
    }

    public void setCaptionNews(String captionNews) {
        this.captionNews = captionNews;
    }

    public String getSourceNews() {
        return sourceNews;
    }

    public void setSourceNews(String sourceNews) {
        this.sourceNews = sourceNews;
    }

    public String getTimeNews() {
        return timeNews;
    }

    public void setTimeNews(String timeNews) {
        this.timeNews = timeNews;
    }
}
