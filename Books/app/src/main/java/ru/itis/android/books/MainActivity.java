package ru.itis.android.books;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.books.models.Article;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private LinearLayoutManager manager;

    private Adapter adapter;

    private List<Article> articles;

    private Toolbar toolbar;

    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        searchView = findViewById(R.id.search_view);


        init();
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new Adapter(articles);
        manager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
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



    public void init(){
        articles = new ArrayList<>();
        articles.add(new Article("Title","Damir","Description","Damir"));
        articles.add(new Article("Title","Damir","Description","Damir"));
        articles.add(new Article("Title","Damir","Description","Damir"));
        articles.add(new Article("Title","Damir","Description","Damir"));
        articles.add(new Article("Title","Damir","Description","Damir"));
    }
}
