package com.example.scastellanos.javaday.ApiService;

import com.example.scastellanos.javaday.CalculatorMVP.models.NewtonModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    public static String BASE_URL = "https://newton.now.sh/";
    private ApiEndpoints newtonApi;

    public ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newton.now.sh/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        newtonApi = retrofit.create(ApiEndpoints.class);
    }

    public Single<NewtonModel> evalExpression(String expr) {
        return newtonApi.simplify(expr);
    }
}