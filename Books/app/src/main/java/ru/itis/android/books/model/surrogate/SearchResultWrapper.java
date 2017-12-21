package ru.itis.android.books.model.surrogate;

import java.util.ArrayList;
import java.util.List;

import ru.itis.android.books.model.bean.Article;
import ru.itis.android.books.model.bean.Doc;
import ru.itis.android.books.model.bean.SearchResult;

/**
 * Created by Users on 21.12.2017.
 */

public class SearchResultWrapper {
    private SearchResult searchResult;

    private List<Article> articles;

    public SearchResultWrapper(SearchResult searchResult) {
        this.searchResult = searchResult;
        initArticles();
    }

    private void initArticles() {
        articles = new ArrayList<>();
        List<Doc> docs = searchResult.getResponse().getDocs();

        String imageURL, webURL, headLine, snippet;         // Данные статьи
        for (Doc doc : docs) {
            imageURL = doc.getMultimedia().get(0).getUrl(); // URL изображения статьи
            webURL = doc.getWebUrl();                       // URL статьи в интернете
            headLine = doc.getHeadline().getMain();         // Заголовок статьи
            snippet = doc.getSnippet();                     // Краткое описание статьи

            articles.add(new Article(
                    imageURL,
                    webURL,
                    headLine,
                    snippet));
        }
    }

    public List<Article> getArticles() {
        return articles;
    }
}
