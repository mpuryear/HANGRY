package com.recipeapp.plip.plipapp.service.adapter;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import com.recipeapp.plip.plipapp.model.SearchResultsModel;

public interface RecipeApiAdapter {

    @GET("recipe")
    Observable<SearchResultsModel> getRecipeSearchResults(
    //        @Query("_app_id") String appId,
            @Query("_app_key") String appKey,
            @Query("q") String searchString);
}