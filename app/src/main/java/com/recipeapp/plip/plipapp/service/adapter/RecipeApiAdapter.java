package com.recipeapp.plip.plipapp.service.adapter;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;

public interface RecipeApiAdapter {


    @Headers("X-Mashape-Key: 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/search?number=10&")
    Observable<ComplexSearchResultsModel> getRecipeSearchResults(
            @Query("offset")String offsetNumber,
            @Query("query") String searchString);



}