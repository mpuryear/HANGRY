package com.recipeapp.plip.plipapp.service.api;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.recipeapp.plip.plipapp.AppDefines;
import com.recipeapp.plip.plipapp.service.adapter.RecipeApiAdapter;

public class ApiClient {

    private static ApiClient instance;

    public static ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }

        return instance;
    }

    public RecipeApiAdapter getRecipeApiAdapter() {
        RecipeApiAdapter api = new Retrofit.Builder()
                .baseUrl(AppDefines.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
                .create(RecipeApiAdapter.class);

        return api;
    }

}
