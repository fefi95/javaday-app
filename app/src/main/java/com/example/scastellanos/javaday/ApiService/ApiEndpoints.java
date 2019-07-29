package com.example.scastellanos.javaday.ApiService;

import com.example.scastellanos.javaday.CalculatorMVP.models.NewtonModel;

import io.reactivex.Observable;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndpoints {

    @GET("simplify/1+1")
    Single<NewtonModel> simplify(@Path("expr") String expr);
}