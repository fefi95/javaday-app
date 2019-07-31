package com.example.scastellanos.javaday.ApiService;

import com.example.scastellanos.javaday.CalculatorMVP.models.NewtonModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpoints {

    @GET("simplify/{expr}")
    Single<NewtonModel> simplify(@Path("expr") String expr);
}