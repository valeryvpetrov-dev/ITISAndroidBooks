package ru.itis.android.books;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.SqlBrite;

import me.tatarka.rxloader.RxLoader;
import me.tatarka.rxloader.RxLoaderManager;
import me.tatarka.rxloader.RxLoaderObserver;
import ru.itis.android.books.model.database.DatabaseManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class MainPresenter {

    private static final String SELECT = "select";

    private static final String INSERT = "insert";

    private RetrofitService retrofitService;

    private MainView view;

    private RxLoaderManager loaderManager;

    private RxLoader<?> loader;

    private DatabaseManager databaseManager;

    private ResponseObject responseObject;

    public MainPresenter(@NonNull RetrofitService retrofitService, @NonNull MainView view,
                         @NonNull RxLoaderManager loaderManager, @NonNull DatabaseManager databaseManager) {
        this.retrofitService = retrofitService;
        this.view = view;
        this.loaderManager = loaderManager;
        this.databaseManager = databaseManager;
    }

    public void load(){
        loader = loaderManager.create(INSERT,
                retrofitService
                        .getData()
                        .flatMap(new Func1<ResponseObject, Observable<Long>>() {
                            @Override
                            public Observable<Long> call(ResponseObject responseObject) {
                                MainPresenter.this.responseObject = responseObject;
                                return  Observable
                                        .concat(databaseManager.getArgsClearQuery(),
                                                databaseManager.getArgsInsertQuery(responseObject.getArgs()));
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
                        view.onLoadingSuccess(responseObject);
                        loader.clear();
                    }
                }).restart();
    }

    public void loadLocal(){
        // TODO select
        loader = loaderManager.create(SELECT,
                databaseManager.getArgsSelectQuery()
                        .map(new Func1<SqlBrite.Query, ResponseObject>() {
                            @Override
                            public ResponseObject call(SqlBrite.Query query) {
                                Cursor cursor = query.run();
                                ResponseObject result = new ResponseObject();
                                if (cursor != null) {
                                    ResponseObjectWrapper wrapper = new ResponseObjectWrapper(cursor);
                                    result.setArgs(wrapper.getArgs());
                                    MainPresenter.this.responseObject = responseObject;
                                    cursor.close();
                                }
                                return result;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()),
                new RxLoaderObserver<ResponseObject>() {
                    @Override
                    public void onError(Throwable e) {
                        view.onLocalLoadingError();
                        loader.clear();
                    }
                    @Override
                    public void onNext(ResponseObject responseObject) {
                        view.onLocalLoadingSuccess(responseObject);
                    }

                    @Override
                    public void onCompleted() {
                        loader.clear();
                    }
                }).start();
    }
}
