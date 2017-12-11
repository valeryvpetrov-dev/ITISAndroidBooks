package ru.itis.android.books;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.books.BookViewHolder;
import ru.itis.android.books.models.Article;

/**
 * Created by Admin on 11.12.2017.
 */

public class Adapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<Article> articles;

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
