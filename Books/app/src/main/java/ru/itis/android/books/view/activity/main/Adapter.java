package ru.itis.android.books.view.activity.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.itis.android.books.R;
import ru.itis.android.books.model.bean.Article;

/**
 * Created by Admin on 11.12.2017.
 */

public class Adapter extends RecyclerView.Adapter<BookViewHolder> {

    private List<Article> articles;

    private OnArticleClickListener listener;

    public Adapter(List<Article> articles){
        this.articles = articles;
        //this.listener = listener;
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        Article item = articles.get(position);

        holder.title.setText(item.getHeadLine());
        // TODO Загрузка картинки по URL из Article.getImageUrl();
        holder.image.setImageResource(R.drawable.color_cursor_white);
        holder.description.setText(item.getSnippet());
        // TODO привязать новые поля
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
