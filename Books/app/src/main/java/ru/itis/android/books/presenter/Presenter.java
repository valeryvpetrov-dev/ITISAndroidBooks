package ru.itis.android.books.presenter;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.SearchResult;
import ru.itis.android.books.model.surrogate.LoaderSearchResult;
import ru.itis.android.books.www.SearchLoader;
import ru.itis.android.books.model.surrogate.SearchResultWrapper;
import ru.itis.android.books.view.ArticleListViewInterface;

/**
 * Created by Users on 21.12.2017.
 */

public class Presenter implements Callback<SearchResult>, LoaderManager.LoaderCallbacks<LoaderSearchResult> {
    private static final int LOADER_ID_SEARCH_ARTICLES = 111;

    private static final String ARGS_SEARCH_KEY_WORD = "keyWord";

    private static Presenter presenter;

    private Application application;
    private Activity activity;
    private ArticleListViewInterface articleListViewInterface;
    private LoaderManager loaderManager;

    private Presenter(Application application,
                      Activity activity,
                      ArticleListViewInterface articleListViewInterface,
                      LoaderManager loaderManager) {
        this.application = application;
        this.activity = activity;
        this.articleListViewInterface = articleListViewInterface;
        this.loaderManager = loaderManager;
    }

//------------------------------------------Presenter API-------------------------------------------
    public static Presenter getInstance(Application application,
                                        Activity activity,
                                        ArticleListViewInterface articleListViewInterface,
                                        LoaderManager loaderManager) {
        if (presenter == null) {
            presenter = new Presenter(application, activity, articleListViewInterface, loaderManager);
        }
        return presenter;
    }

    // Метод запуска поиска по ключевому слову
    public void searchByKeyWord(String keyWord) {
        Bundle searchArgs = new Bundle();
        searchArgs.putString(ARGS_SEARCH_KEY_WORD, keyWord);

        loaderManager.initLoader(LOADER_ID_SEARCH_ARTICLES,
                searchArgs,
                this);
    }

//---------------------------------------Многопоточность--------------------------------------------
    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_ID_SEARCH_ARTICLES:
                return new SearchLoader(
                        activity.getApplicationContext(),
                        ((ArticlesApp) application).getSearchApi(),
                        this,
                        args.getString(ARGS_SEARCH_KEY_WORD));
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, LoaderSearchResult loaderSearchResult) {
        if (loaderSearchResult != null) {
            switch (loader.getId()) {
                case LOADER_ID_SEARCH_ARTICLES:
                    articleListViewInterface.showSearchResult(loaderSearchResult);
                    break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

//------------------------------------Обработка HTTP-ответа-----------------------------------------
    @Override
    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
        if (response.isSuccessful()) {
            SearchResult searchResult = response.body();
            onLoadFinished(
                    loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                    new LoaderSearchResult(new SearchResultWrapper(searchResult)));
        } else {
            try {
                onLoadFinished(
                        loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                        new LoaderSearchResult(response.errorBody().string())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<SearchResult> call, Throwable t) {
        onLoadFinished(
                loaderManager.getLoader(LOADER_ID_SEARCH_ARTICLES),
                new LoaderSearchResult(t.getMessage())
        );
    }
}
