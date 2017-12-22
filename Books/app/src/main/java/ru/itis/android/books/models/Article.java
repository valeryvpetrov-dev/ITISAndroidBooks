package ru.itis.android.books.models;

/**
 * Created by Admin on 11.12.2017.
 */

public class Article {

    private String title;

    private String image;

    private String description;

    private String url;

    public Article(String title, String image, String description,String url) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
