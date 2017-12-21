package ru.itis.android.books;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Admin on 11.12.2017.
 */

public class BookViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;

    public TextView title;

    public TextView description;

    public BookViewHolder(View itemView, OnArticleClickListener listener) {
        super(itemView);

        image = itemView.findViewById(R.id.image_view1);

        title = itemView.findViewById(R.id.title_text_view);

        description = itemView.findViewById(R.id.description_text_view);
    }
}
