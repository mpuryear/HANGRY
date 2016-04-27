package com.recipeapp.plip.plipapp.model;


import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * A container for recipe search results.  Results are delivered as an array, so a wrapping container
 * must be created for deserialization using Gson
 */
public class ComplexSearchResultsModel {

    @SerializedName("results")
    ArrayList<ComplexRecipeItemModel> searchResults;

    public ArrayList<ComplexRecipeItemModel> getSearchResults() {
        return searchResults;
    }

}