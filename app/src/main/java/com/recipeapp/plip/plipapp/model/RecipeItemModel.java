package com.recipeapp.plip.plipapp.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An application model DTO for holding data for individual recipes in search results.  Implmements
 * the Serializable interface to all the class to be serialized into fragment args.
 */
public class RecipeItemModel implements Serializable {
    private Integer id;
    private String title;
    private Integer readyInMinutes;
    private ArrayList<String> imageUrls;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getReadyInMinutes() {
        return readyInMinutes;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }
}