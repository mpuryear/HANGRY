package com.recipeapp.plip.plipapp.utility;

import com.google.gson.Gson;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;

/**
 * A class which takes in a JSON stirng and returns a ComplexSearchResultsModel
 */
public class RecipeParser {
    public static final ComplexSearchResultsModel parseRecipeFromJson(final String inputString) {
        ComplexSearchResultsModel complexSearchResultsModel;
        Gson gson = new Gson();
        complexSearchResultsModel = gson.fromJson(inputString, ComplexSearchResultsModel.class);

        return complexSearchResultsModel;
    }
}