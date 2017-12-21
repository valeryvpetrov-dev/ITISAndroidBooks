package ru.itis.android.books.view.main;

import android.support.annotation.NonNull;

import java.util.List;

import ru.itis.android.books.model.bean.Article;


public interface MainView {

    void onLoadingSuccess(@NonNull List<Article> responseObject);

    void onLoadingError();

    void onLocalLoadingSuccess(@NonNull List<Article> responseObject);

    void onLocalLoadingError();

}
