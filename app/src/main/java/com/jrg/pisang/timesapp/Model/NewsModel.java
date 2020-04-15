package com.jrg.pisang.timesapp.Model;

public class NewsModel {

    String titleNews, sourceNews, dateNews;

    public NewsModel(String titleNews, String sourceNews, String dateNews) {
        this.titleNews = titleNews;
        this.sourceNews = sourceNews;
        this.dateNews = dateNews;
    }

    public String getTitleNews() {
        return titleNews;
    }

    public void setTitleNews(String titleNews) {
        this.titleNews = titleNews;
    }

    public String getSourceNews() {
        return sourceNews;
    }

    public void setSourceNews(String sourceNews) {
        this.sourceNews = sourceNews;
    }

    public String getDateNews() {
        return dateNews;
    }

    public void setDateNews(String dateNews) {
        this.dateNews = dateNews;
    }
}

