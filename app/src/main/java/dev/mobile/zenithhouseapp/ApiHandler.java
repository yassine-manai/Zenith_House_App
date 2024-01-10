package dev.mobile.zenithhouseapp;



import java.util.List;
import retrofit.Call;
import retrofit.http. Field;
import retrofit.http. FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
public interface ApiHandler
{
    @GET("ZHA/list.php")
    Call<List<feeds>> getAllfeeds();

    @FormUrlEncoded
    @POST("ZHA/insert.php")
    Call<feeds> insertfeeds(
                            @Field("name") String name,
                            @Field("number") String number,
                            @Field("feed") String feed);

    @FormUrlEncoded
    @POST("ZHA/update.php")
    Call<feeds> updatetfeeds(@Field("id") int id,
                             @Field("name") String name,
                             @Field("number") String number,
                             @Field("feed") String feed);
    @FormUrlEncoded
    @POST("ZHA/delete.php")
    Call<Void> deletefeeds(@Field("id") int id);
}