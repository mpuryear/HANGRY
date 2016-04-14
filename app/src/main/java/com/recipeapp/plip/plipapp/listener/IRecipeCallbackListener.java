package com.recipeapp.plip.plipapp.listener;


import com.recipeapp.plip.plipapp.model.SearchResultsModel;

/**
 * A simple interface to allow implementation of a Listener pattern for managing notification
 * of completed background threaded tasks managed by the RecipeSearchTask
 */
public interface IRecipeCallbackListener {
    void onSearchCallback(SearchResultsModel searchResultsModel);
}