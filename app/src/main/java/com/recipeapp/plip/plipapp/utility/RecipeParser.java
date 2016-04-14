package com.recipeapp.plip.plipapp.utility;

import com.google.gson.Gson;
import com.recipeapp.plip.plipapp.model.SearchResultsModel;

/**
 * A class which takes in a JSON stirng and returns a SearchResultsModel
 */
public class RecipeParser {
    public static final SearchResultsModel parseRecipeFromJson(final String inputString) {
        SearchResultsModel searchResultsModel;
        Gson gson = new Gson();
        searchResultsModel = gson.fromJson(inputString, SearchResultsModel.class);

        return searchResultsModel;
    }
}