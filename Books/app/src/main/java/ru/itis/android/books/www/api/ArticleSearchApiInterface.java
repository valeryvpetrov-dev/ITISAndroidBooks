package ru.itis.android.books.www.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.itis.android.books.model.bean.SearchResult;

/**
 * Created by Users on 21.12.2017.
 */

// Фильтрация необходимых данных: ?fl=headline&fl=snippet&fl=web_url&fl=multimedia
public interface ArticleSearchApiInterface {
    String API_KEY = "80aaca9aaf7d42d7bcf17e2af51815c7"; // ключ для получения для авторизации на ресурсу

    String BASE_URL = "http://api.nytimes.com/";
    String BASE_URL_PATH = "svc/search/v2/articlesearch.json";

    @GET(BASE_URL_PATH)
    Call<SearchResult> getArticlesByKeyWord(@Query("fq") String keyWord, @Query("api-key") String apiKey);
}
