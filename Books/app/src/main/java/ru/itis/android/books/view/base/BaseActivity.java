package ru.itis.android.books.view.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.R;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;

/**
 * Created by User on 11.12.2017.
 */
// TODO подгрузка картинок из сети (GLIDE)
public abstract class BaseActivity extends AppCompatActivity implements MainView {
    protected Toolbar toolbar;

    protected Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

//        toolbar = (Toolbar) findViewById(getToolbarId());
//        setSupportActionBar(toolbar);

//        FragmentManager fm = getFragmentManager();
//        if(fm.findFragmentById(getContainerId()) == null) {
//            fm.beginTransaction()
//                    .add(getContainerId(), getFragment())
//                    .commit();
//        }
    }

    @NonNull
    protected abstract Fragment getFragment();

    protected int getLayoutResId() {
        return R.layout.activity_base;
    }

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    private int getContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public abstract void onSearchSuccess(@NonNull List<Article> articles);

    @Override
    public abstract void onSearchError(String message);

    @Override
    public abstract void onLoadingPreviousSearchResultSuccess(@NonNull List<Article> articles);

    @Override
    public abstract void onLoadingPreviousSearchResultError(String message);
}
