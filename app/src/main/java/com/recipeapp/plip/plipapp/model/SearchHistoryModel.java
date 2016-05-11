package com.recipeapp.plip.plipapp.model;

import java.io.Serializable;

/**
 * Created by SugarPalaces on 4/19/16.
 */
public class SearchHistoryModel implements Serializable{
    //private Integer id;
    private String title;
    private String imageUrl;
    private String allergies;
    private String cuisine;
    private String mealType;

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title;}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
