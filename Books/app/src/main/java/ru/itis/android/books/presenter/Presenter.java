package ru.itis.android.books.presenter;

import android.app.Application;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.rxloader.RxLoader;
import me.tatarka.rxloader.RxLoaderManager;
import me.tatarka.rxloader.RxLoaderObserver;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.bean.SearchResult;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.model.surrogate.SearchResultWrapper;
import ru.itis.android.books.model.wrapper.ArticleWrapper;
import ru.itis.android.books.view.ArticleListViewInterface;
import ru.itis.android.books.view.main.MainView;
import ru.itis.android.books.www.api.ArticleSearchApiInterface;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Users on 21.12.2017.
 */

public class Presenter {
    private static final int LOADER_ID_SEARCH_ARTICLES = 111;

    private static final String SELECT_LOADER = "select";
    private static final String INSERT_LOADER = "insert";

//    private static final String ARGS_SEARCH_KEY_WORD = "keyWord";

    private static Presenter presenter;

    private List<Article> articles;

    private Application application;
    private MainView view;
    private ArticleSearchApiInterface retrofitService;
    private ArticleListViewInterface articleListViewInterface;
    private RxLoaderManager loaderManager;
    private DatabaseManager databaseManager;

    private RxLoader loader;

    /*private Presenter(Application application,
                      MainView view,
                      ArticleListViewInterface articleListViewInterface,
                      @NonNull RxLoaderManager loaderManager,
                      @NonNull DatabaseManager databaseManager) {
        this.application = application;
        this.view = view;
        this.articleListViewInterface = articleListViewInterface;
        this.loaderManager = loaderManager;
        this.databaseManager = databaseManager;
    }*/

    private Presenter(@NonNull ArticleSearchApiInterface articleSearchApiInterface,
                      @NonNull MainView view,
                      @NonNull RxLoaderManager loaderManager,
                      @NonNull DatabaseManager databaseManager) {
        this.retrofitService = articleSearchApiInterface;
        this.view = view;
        this.loaderManager = loaderManager;
        this.databaseManager = databaseManager;
    }

//------------------------------------------Presenter API-------------------------------------------
    /*public static Presenter getInstance(Application application,
                                        @NonNull MainView view,
                                        ArticleListViewInterface articleListViewInterface,
                                        @NonNull RxLoaderManager loaderManager,
                                        @NonNull DatabaseManager databaseManager) {
        if (presenter == null) {
            presenter = new Presenter(application, view, articleListViewInterface, loaderManager,databaseManager);
        }
        return presenter;
    }*/

    public static Presenter getInstance(@NonNull ArticleSearchApiInterface articleSearchApiInterface,
                                        @NonNull MainView view,
                                        @NonNull RxLoaderManager loaderManager,
                                        @NonNull DatabaseManager databaseManager) {
        if (presenter == null) {
            presenter = new Presenter(articleSearchApiInterface, view,loaderManager,databaseManager);
        }
        return presenter;
    }

    // Метод запуска поиска по ключевому слову
    public void searchByKeyWord(String keyWord) {
        /*Bundle searchArgs = new Bundle();
        searchArgs.putString(ARGS_SEARCH_KEY_WORD, keyWord);*/

        load(keyWord);

        /*loaderManager.initLoader(LOADER_ID_SEARCH_ARTICLES,
                searchArgs,
                this);*/
    }

//---------------------------------------Многопоточность--------------------------------------------
    /*@Override
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

    }*/

//------------------------------------Обработка HTTP-ответа-----------------------------------------
   /* @Override
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
    }*/

    public void load(String keyWord){
        loader = loaderManager.create(INSERT_LOADER,
                retrofitService
                        .getArticlesByKeyWord(keyWord,ArticleSearchApiInterface.API_KEY)
                        .flatMap(new Func1<SearchResult, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(SearchResult result) {
                                SearchResultWrapper searchResult = new SearchResultWrapper(result);
                                articles = searchResult.getArticles();
                                return  Observable
                                        .concat(databaseManager.getArgsClearQuery(),
                                                databaseManager.getArticlesInsertQuery(articles));
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<Long>() {
                    @Override
                    public void onNext(Long value) {}

                    @Override
                    public void onError(Throwable e) {
                        view.onLoadingError();
                        loader.clear();
                    }

                    @Override
                    public void onCompleted() {
                        view.onLoadingSuccess(articles);
                        loader.clear();
                    }
                }).restart();
    }

    public void loadLocal(){
        // TODO select
        loader = loaderManager.create(SELECT_LOADER,
                databaseManager.getArticlesSelectQuery()
                        .map(new Func1<SqlBrite.Query, List<Article>>() {
                            @Override
                            public List<Article> call(SqlBrite.Query query) {
                                Cursor cursor = query.run();
                                articles = new ArrayList<>();
                                if (cursor != null) {
                                    ArticleWrapper wrapper = new ArticleWrapper(cursor);
                                    articles.addAll(wrapper.getArticles());
                                    cursor.close();
                                }
                                return articles;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<List<Article>>() {
                    @Override
                    public void onError(Throwable e) {
                        view.onLocalLoadingError();
                        loader.clear();
                    }
                    @Override
                    public void onNext(List<Article> articles) {
                        view.onLocalLoadingSuccess(articles);
                    }

                    @Override
                    public void onCompleted() {
                        loader.clear();
                    }
                }).start();
    }
}
