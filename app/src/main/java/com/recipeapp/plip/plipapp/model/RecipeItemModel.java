package com.recipeapp.plip.plipapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An application model DTO for holding data for individual recipes in search results.  Implmements
 * the Serializable interface to all the class to be serialized into fragment args.
 */
public class RecipeItemModel implements Serializable {
    private String id;
    private String recipeName;
    private ArrayList<String> smallImageUrls;
    private ArrayList<String> ingredients;;

    public String getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public ArrayList<String> getSmallImageUrls() {
        return smallImageUrls;
    }

    public ArrayList<String> getIngredients() { return ingredients; }


}