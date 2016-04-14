package com.recipeapp.plip.plipapp.utility;


import com.recipeapp.plip.plipapp.AppDefines;

public class UrlFormatUtility {
    private static final String RECIPE_ROUTE = "/recipes?";

    public static String formatRecipeSearchString(String searchString) {
        String urlString = AppDefines.BASE_API_URL +
                RECIPE_ROUTE +
                "_app_id=" +
                AppDefines.APPLICATION_ID +
                "&_app_key=" +
                AppDefines.APPLICATION_KEY +
                "&q=" + searchString;

        return urlString;
    }
}
