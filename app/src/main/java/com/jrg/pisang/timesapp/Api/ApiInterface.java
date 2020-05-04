package com.jrg.pisang.timesapp.Api;

import com.jrg.pisang.timesapp.Model.DetailKanalModel;
import com.jrg.pisang.timesapp.Model.FokusDetailModel;
import com.jrg.pisang.timesapp.Model.EKoranDetailModel;
import com.jrg.pisang.timesapp.Model.EkoranModel;
import com.jrg.pisang.timesapp.Model.FokusModel;
import com.jrg.pisang.timesapp.Model.FotoDetailModel;
import com.jrg.pisang.timesapp.Model.FotoModel;
import com.jrg.pisang.timesapp.Model.HeadlineModel;
import com.jrg.pisang.timesapp.Model.KanalModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //    https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=populer&offset=0&limit=10
    //    - Trending news_type=trending
    //    - HeadlineModel news_type = headline

    @GET("all_news/")
    Call<HeadlineModel> getNewsHeadline(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    //https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=related_new&news_id=260518&title=virus+corona&offset=0&limit=10
    @GET("all_news/")
    Call<HeadlineModel> getNewsRelated(
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
    Call<EkoranModel> getEKoran(
            @Query("key") String key,
            @Query("offset") int offset,
            @Query("limit") int limit
    );

    //Koran_detail/1249
    @GET("koran_detail/{id}")
    Call<EKoranDetailModel> getEKoranDetail(
            @Path(value = "id", encoded = true) int id,
            @Query("key") String key
    );

    //https://api.timesindonesia.co.id/v1/kanal/?key=NyEIwDL51eeaoVhYGPaF
    //kanal
    @GET("kanal/")
    Call<KanalModel> getKanal(
            @Query("key") String key
    );

    //https://api.timesindonesia.co.id/v1/cat_detail/peristiwa-nasional?key=NyEIwDL51eeaoVhYGPaF
    //detailkanal
    @GET("cat_detail/{catnews_title}")
    Call<DetailKanalModel> getDetailKanal(
            @Path(value = "catnews_title", encoded = true) String catnews_title,
            @Query("key") String key
    );


    //https://api.timesindonesia.co.id/v1/list_focus/?key=NyEIwDL51eeaoVhYGPaF&offset=0&limit=5
    //fokus
    @GET("list_focus/")
    Call<FokusModel> getFokus(
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

    //https://api.timesindonesia.co.id/v1/foto_detail/1435?key=NyEIwDL51eeaoVhYGPaF
    @GET("foto_detail/{id}")
    Call<FotoDetailModel> getFotoDetail(
            @Path(value = "id", encoded = true) int id,
            @Query("key") String key
    );

    //https://api.timesindonesia.co.id/v1/focus_detail/221?key=NyEIwDL51eeaoVhYGPaF
    @GET("focus_detail/{id}")
    Call<FokusDetailModel> getFokusDetail(
            @Path(value = "id", encoded = true) int id,
            @Query("key") String key
    );

    //https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=focus&cat_id=212&offset=0&limit=10
    @GET("all_news/")
    Call<HeadlineModel> getListFokus(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query(value = "cat_id", encoded = true) int cat_id,
            @Query("offset") int offset,
            @Query("limit") int limit
    );



    ////https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=tag&title=presiden%20jokowi&offset=0&limit=10
    //  https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=search&title=presiden+jokowi&offset=0&limit=10
    //https://api.timesindonesia.co.id/v1/all_news/?key=NyEIwDL51eeaoVhYGPaF&news_type=tag&title=timesvlog&offset=0&limit=10
    @GET("all_news/")
    Call<HeadlineModel> getListTag(
            @Query("key") String key,
            @Query("news_type") String news_type,
            @Query("title") String title,
            @Query("offset") int offset,
            @Query("limit") int limit
    );
}
