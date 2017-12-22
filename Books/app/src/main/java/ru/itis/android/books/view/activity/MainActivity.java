package ru.itis.android.books.view.activity;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;
import ru.itis.android.books.view.base.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = Presenter.getInstance(
                ((ArticlesApp)getApplication()).getSearchApi(),
                this,
                RxLoaderManager.get(this),
                new DatabaseManager(this));
    }

    @NonNull
    @Override
    protected Fragment getFragment() {
        return null;
    }

    @Override
    public void onSearchSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат поиска
    }

    @Override
    public void onSearchError(String message) {
        // TODO отобразить ошибку при загруке стетей
    }

    @Override
    public void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> articles) {
        // TODO отобразить результат данных из кэша(бд)
    }

    @Override
    public void onLoadingPreviousSearchResultError(String message) {
        // TODO отобразить ошибку при загруке из кэша(бд)
    }

    // TODO запустить событие поиска статьи:
    // presenter.searchByKeyWord(keyWord);
}
