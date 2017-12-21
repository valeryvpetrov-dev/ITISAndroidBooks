package ru.itis.android.books.model.table;

import android.support.annotation.NonNull;

/**
 * Created by Ruslan on 21.12.2017.
 */

public class ArticleTable {

    public static final String NAME = "articles";

    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_HEADLINE = "headline";

    public static final String COLUMN_SNIPPET = "snippet";

    public static final String COLUMN_ARTICLE_URL = "article_url";

    public static final String COLUMN_IMAGE_URL = "image_url";

    @NonNull
    public static String getCreateQuery(){
        return "CREATE TABLE " + NAME + " (" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                COLUMN_HEADLINE + " TEXT NON NULL," +
                COLUMN_SNIPPET + " TEXT NON NULL," +
                COLUMN_ARTICLE_URL + " TEXT NON NULL," +
                COLUMN_SNIPPET + " TEXT NON NULL);";
    }

    @NonNull
    public static String getSelectQuery(){
        return "SELECT * FROM " + NAME + ";";
    }


}
