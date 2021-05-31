package com.coin.whichfood;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.Retrofit.*;

public class RetrofitClient {
    private static final String Base_Url = "http://api.openweathermap.org/";
    public static ApiService apiService() {
        return getInstance().create(ApiService.class);
    }

    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    
}
