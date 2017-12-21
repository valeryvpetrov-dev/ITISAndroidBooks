package ru.itis.android.books.model.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model2.table.ArgTable;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class ArticleWrapper extends CursorWrapper {

    public ArticleWrapper(Cursor cursor) {
        super(cursor);
    }

    @Nullable
    private Article getArticle(){
        if(!isBeforeFirst() && !isAfterLast()) {

            String headLine = getString(getColumnIndex(ArgTable.COLUMN_HEADLINE));
            String snippet = getString(getColumnIndex(ArgTable.COLUMN_SNIPPET));
            String webUrl = getString(getColumnIndex(ArgTable.COLUMN_ARTICLE_URL));
            String imageUrl = getString(getColumnIndex(ArgTable.COLUMN_IMAGE_URL));

            return new Article(headLine,snippet,webUrl,imageUrl);
        }
        return null;
    }

    @NonNull
    public List<Article> getArticles() {
        List<Article> articles = new ArrayList<>();
        moveToFirst();
        while (!isBeforeFirst() && !isAfterLast()){
            Article article = getArticle();
            if(article != null) {
                articles.add(article);
            }
            moveToNext();
        }
        return articles;
    }
}
