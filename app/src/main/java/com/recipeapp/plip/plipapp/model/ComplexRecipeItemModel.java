package com.recipeapp.plip.plipapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An application model DTO for holding data for individual recipes in search results.  Implmements
 * the Serializable interface to all the class to be serialized into fragment args.
 */
public class ComplexRecipeItemModel implements Serializable {
    private String id;
   /*
    private String usedIngredientCount;
    private String missedIngredientCount;
    private String likes;
    */
    private String title;
    private String image;
    private String imageType;
    /*
    private String calories;
    private String protein;
    private String fat;
    private String carbs;
*/
    public String getId() {
        return id;
    }
/*
    public String getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public String getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public String getLikes() {
        return likes;
    }
*/
    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

//    public String getCalories() {
//        return calories;
//    }
//
//    public String getProtein() {
//        return protein;
//    }
//
//    public String getFat() {
//        return fat;
//    }
//
//    public String getCarbs() {
//        return carbs;
//    }
}