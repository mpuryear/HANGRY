package com.recipeapp.plip.plipapp.model;

import java.util.ArrayList;

/**
 * Created by Dulce Palacios on 4/17/16.
 */
public class SearchRecipeModel {
    private ArrayList<RecipeItemModel> results;
    private String baseUri;
    private Integer offset;
    private Integer number;
    private Integer totalResults;
    private Integer processingTimeMs;
    private Integer expires;
    private Boolean isStale;
}
