package com.coin.whichfood;

import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("data/2.5/weather")
    Call<ApiModel> getWeather(@Query("q") String q, @Query("appid") String appid);
}
