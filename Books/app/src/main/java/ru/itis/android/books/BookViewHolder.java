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

    public BookViewHolder(View itemView) {
        super(itemView);
    }
}
