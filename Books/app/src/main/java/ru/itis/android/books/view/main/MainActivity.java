package ru.itis.android.books.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import ru.itis.android.books.presenter.Presenter;
import ru.itis.android.books.R;
import ru.itis.android.books.model.surrogate.LoaderSearchResult;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.surrogate.SearchResultWrapper;
import ru.itis.android.books.view.ArticleListViewInterface;

// TODO подгрузка картинок из сети (GLIDE). Хост картинок - https://cdn1.nyt.com/
public class MainActivity extends AppCompatActivity implements ArticleListViewInterface {
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = Presenter.getInstance(getApplication(), this, this, getSupportLoaderManager());
        presenter.searchByKeyWord("Russia");
    }

    @Override
    public void showSearchResult(LoaderSearchResult loaderSearchResult) {
        if (loaderSearchResult.getErrorMessage() != null) {
            // TODO отобразить ошибку при загруке стетей
        } else if (loaderSearchResult.getSearchResultWrapper() != null) {
            // TODO отобразить результат поиска
            SearchResultWrapper searchResult = loaderSearchResult.getSearchResultWrapper();
            List<Article> articles = searchResult.getArticles();
        }
    }

    // TODO запустить событие поиска статьи: presenter.searchByKeyWord(keyWord);
}
