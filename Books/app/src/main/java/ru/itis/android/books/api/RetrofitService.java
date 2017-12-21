package ru.itis.android.books.api;

import com.example.ruslan.rxjavahw.model.entity.ResponseObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitService {

    @GET("/get?num1=5&num2=3&result=8")
    Observable<ResponseObject> getData();

    @POST("/post")
    Call<ResponseBody> postData();

}
