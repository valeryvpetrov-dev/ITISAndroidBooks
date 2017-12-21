package ru.itis.android.books.view.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.R;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;

// TODO подгрузка картинок из сети (GLIDE). Хост картинок - https://cdn1.nyt.com/
public class MainActivity extends AppCompatActivity implements MainView {
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        presenter = Presenter.getInstance(getApplication(), this, this, getSupportLoaderManager());
        presenter = Presenter.getInstance(((ArticlesApp)getApplication()).getSearchApi(), this, RxLoaderManager.get(this), new DatabaseManager(this));
        presenter.searchByKeyWord("Russia");
    }

    /*@Override
    public List<Article> showSearchResult(LoaderSearchResult loaderSearchResult) {
        List<Article> articles = new ArrayList<>();
        if (loaderSearchResult.getErrorMessage() != null) {
            // TODO отобразить ошибку при загруке стетей
        } else if (loaderSearchResult.getSearchResultWrapper() != null) {
            // TODO отобразить результат поиска
            SearchResultWrapper searchResult = loaderSearchResult.getSearchResultWrapper();
             articles = searchResult.getArticles();
        }
        return articles;
    }*/

    @Override
    public void onLoadingSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат поиска
    }

    @Override
    public void onLoadingError() {
        // TODO отобразить ошибку при загруке стетей
    }

    @Override
    public void onLocalLoadingSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат данных из кэша(бд)
    }

    @Override
    public void onLocalLoadingError() {
        // TODO отобразить ошибку при загруке из кэша(бд)
    }

    // TODO запустить событие поиска статьи: presenter.searchByKeyWord(keyWord);
}
