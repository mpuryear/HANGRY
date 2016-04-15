package com.recipeapp.plip.plipapp.service;

import android.os.AsyncTask;

import java.io.IOException;

import com.recipeapp.plip.plipapp.listener.IRecipeCallbackListener;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.utility.RecipeParser;
import com.recipeapp.plip.plipapp.utility.UrlFormatUtility;

/**
 * A class extending the AsyncTask abstract class with the specific intent of returning a ComplexSearchResultsModel
 * from a remote API call
 */
public class RecipeSearchTask extends AsyncTask<String,String,ComplexSearchResultsModel> {

    // A local instance of a listener interface
    private IRecipeCallbackListener recipeCallbackListener;

    // A default constructor requiring an instance of IRecipeCallbackListener to assign to a private
    // member variable fo the same type
    public RecipeSearchTask (final IRecipeCallbackListener recipeCallbackListener) {
        this.recipeCallbackListener = recipeCallbackListener;
    }

    // An overridden virtual method which executes it's contained body of code on a background thread
    @Override
    protected ComplexSearchResultsModel doInBackground(String... params) {
        // Create an http handler
        HttpRequestManager httpRequestManager = new HttpRequestManager();
        String response = "";

        try {
            // get the response from the remote http call
            response = httpRequestManager.getRecipe(UrlFormatUtility.formatRecipeSearchString(params[0]));
        } catch (IOException exception) {
            String exceptionString = exception.getMessage();
        }

        // return a SearchResultModel via the {@link com.recipeapp.plip.plipapp.utility.RecipeParser}
        return RecipeParser.parseRecipeFromJson(response);
    }

    // An overridden virtual method which handles the value returned from the background thread to the
    // main thread
    @Override
    protected void onPostExecute(ComplexSearchResultsModel complexSearchResultsModel) {
        super.onPostExecute(complexSearchResultsModel);
        // Invoke the local listener which has a reference to the concretely implemented listener in SearchActivity
        recipeCallbackListener.onSearchCallback(complexSearchResultsModel);
    }
}