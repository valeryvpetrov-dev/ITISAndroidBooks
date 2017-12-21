package ru.itis.android.books.model.bean;

import android.support.annotation.Nullable;

/**
 * Created by Users on 21.12.2017.
 */

public class Article {
    private String imageURL;
    private String webURL;
    private String headLine;
    private String snippet;

    public Article(@Nullable String imageURL, String webURL, String headLine, String snippet) {
        this.imageURL = imageURL;
        this.webURL = webURL;
        this.headLine = headLine;
        this.snippet = snippet;
    }

    @Nullable
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(@Nullable String imageURL) {
        this.imageURL = imageURL;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
