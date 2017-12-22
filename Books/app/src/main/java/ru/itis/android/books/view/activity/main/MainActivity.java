package ru.itis.android.books.view.activity.main;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import me.tatarka.rxloader.RxLoaderManager;
import ru.itis.android.books.R;
import ru.itis.android.books.app.ArticlesApp;
import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.database.DatabaseManager;
import ru.itis.android.books.presenter.Presenter;
import ru.itis.android.books.view.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager manager;
    private Adapter adapter;
    private Toolbar toolbar;
    private MaterialSearchView searchView;

    private List<Article> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = Presenter.getInstance(
                ((ArticlesApp)getApplication()).getSearchApi(),
                this,
                RxLoaderManager.get(this),
                new DatabaseManager(this));


        presenter.loadPreviousSearchResult();

        initView();
    }

    private void initView() {
        toolbar = findViewById(getToolbarId());
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        searchView = findViewById(R.id.search_view);

        recyclerView = findViewById(R.id.recycler_view);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showSearch(false);
        searchView.clearFocus();

        return true;
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
