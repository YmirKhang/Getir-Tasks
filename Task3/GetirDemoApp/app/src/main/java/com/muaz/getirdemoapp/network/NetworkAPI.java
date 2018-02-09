package com.muaz.getirdemoapp.network;

import com.muaz.getirdemoapp.network.GetirService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muazekici on 8.02.2018.
 */

public class NetworkAPI {

    static String BASE_URL = "https://getir-bitaksi-hackathon.herokuapp.com/";
    private static GetirService instance;


    public static GetirService getInstance(){

        if(instance != null){
            return instance;
        }


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();


        instance = retrofit.create(GetirService.class);
        return instance;
    }
}
