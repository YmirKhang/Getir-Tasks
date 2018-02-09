package com.muaz.getirdemoapp.network;

import com.muaz.getirdemoapp.models.RequestObj;
import com.muaz.getirdemoapp.models.objResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by muazekici on 8.02.2018.
 */

public interface GetirService {



    @POST("searchRecords")
    Call<objResponse> getSearchList(@Body RequestObj requestObj);
}
