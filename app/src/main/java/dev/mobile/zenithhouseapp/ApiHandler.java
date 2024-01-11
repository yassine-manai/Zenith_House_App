package dev.mobile.zenithhouseapp;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface ApiHandler {

    @GET("ZHA/list.php")
    Call<List<feeds>> getAllfeeds();

    @POST("ZHA/insert.php")
    Call<feeds> insertfeeds(@Body FeedRequestBody requestBody);

    @POST("ZHA/update.php")
    Call<feeds> updatetfeeds(@Body FeedRequestBody requestBody);

    @POST("ZHA/delete.php")
    Call<Void> deletefeeds(@Body FeedIdRequestBody requestBody);
}
