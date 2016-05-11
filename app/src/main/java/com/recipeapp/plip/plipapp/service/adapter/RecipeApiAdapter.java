package com.recipeapp.plip.plipapp.service.adapter;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.RecipeInformationModel;
import com.recipeapp.plip.plipapp.model.SummarySearchResultsModel;

public interface RecipeApiAdapter {


    @Headers("X-Mashape-Key: 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/search?number=60&")
    Observable<ComplexSearchResultsModel> getRecipeSearchResults(
            @Query("intolerances") String allergies,
            @Query("cuisine") String cuisineType,
            @Query("type") String mealType,
            @Query("offset")String offsetNumber,
            @Query("query") String searchString);



     // This is used to get the information as we go into our final fragment view
    @Headers("X-Mashape-Key: 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/{id}/summary")
    Observable<SummarySearchResultsModel> getSummarySearchResults(
            @Path("id") int id);

    @Headers("X-Mashape-Key: 9WLvQTpY6pmshsB2bkvHaMWU6MaJp1ROaugjsnPbkqB5D8i082")
    @GET("recipes/{id}/information")
    Observable<RecipeInformationModel> getRecipeInformationResults(
        @Path("id") int id);


}