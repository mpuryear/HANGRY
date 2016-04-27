package com.recipeapp.plip.plipapp.service.adapter;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.QuickAnswerModel;

public interface RecipeApiAdapter {


    @Headers("X-Mashape-Key: 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/searchComplex?limitLicense=false&number=10&offset=0&")
    Observable<ComplexSearchResultsModel> getRecipeSearchResults(
            @Query("query") String searchString);


    @Headers("X-Mashape-Key : 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/quickAnswer")
    Observable<QuickAnswerModel> getQuickAnswer(
            @Query("q") String searchString
    );

}