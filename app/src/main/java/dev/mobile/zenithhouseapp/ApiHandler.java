package dev.mobile.zenithhouseapp;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface ApiHandler {

    @GET("ZHA/list.php")
    Call<List<feeds>> getAllfeeds();

    @POST("ZHA/insert.php")
    Call<feeds> insertfeeds(@Body feeds requestBody);

    @POST("ZHA/insertuser.php")
    Call<Void> insertuser(@Body User user);

    @GET("ZHA/getusers.php")
    Call<List<User>> loginUser(
            @Query("email") String email,
            @Query("password") String password
    );

    @POST("ZHA/update.php")
    Call<feeds> updatetfeeds(@Body feeds requestBody);

    @POST("ZHA/delete.php")
    Call<Void> deletefeeds(@Body feeds requestBody);
}
