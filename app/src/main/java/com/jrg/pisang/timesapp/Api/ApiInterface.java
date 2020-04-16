package com.jrg.pisang.timesapp.Api;

import com.jrg.pisang.timesapp.Model.DataKoranDetail;
import com.jrg.pisang.timesapp.Model.Ekoran;
import com.jrg.pisang.timesapp.Model.Fokus;
import com.jrg.pisang.timesapp.Model.FotoModel;
import com.jrg.pisang.timesapp.Model.Headline;
import com.jrg.pisang.timesapp.Model.DataFokus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //    https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=populer&offset=0&limit=10
    //    - Trending news_type=trending
    //    - Headline news_type = headline

    @GET("all_news/")
    Call<Headline> getNewsHeadline(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    @GET("all_news/")
    Call<Headline> getNewsRelated(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query(value = "news_id", encoded = true) int news_id,
            @Query("title") String title,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    //    https://api.timesindonesia.co.id/v1/all_ekoran/?key=NyEIwDL51eeaoVhYGPaF&offset=0&limit=10
    //ekoran
    @GET("all_ekoran/")
    Call<Ekoran> getEKoran(
            @Query("key") String key,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    //Koran_detail/1249
    @GET("koran_detail/{id}")
    Call<DataKoranDetail> getEKoranDetail(
            @Path(value = "id", encoded = true) int id,
            @Query("key") String key
    );

    //https://api.timesindonesia.co.id/v1/list_focus/?key=NyEIwDL51eeaoVhYGPaF&offset=0&limit=5
    //fokus
    @GET("list_focus/")
    Call<Fokus> getFokus(
            @Query("key") String key,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

   // https://api.timesindonesia.co.id/v1/all_gallery/?key=NyEIwDL51eeaoVhYGPaF&news_type=all&offset=0&limit=10
    @GET("all_gallery/")
    Call<FotoModel> getFoto(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query("offset") int offset,
            @Query("limit") int limit
    );
}
