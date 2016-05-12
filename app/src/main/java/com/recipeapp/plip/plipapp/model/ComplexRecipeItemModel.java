package com.recipeapp.plip.plipapp.model;

import com.recipeapp.plip.plipapp.AppDefines;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An application model DTO for holding data for individual recipes in search results.  Implmements
 * the Serializable interface to all the class to be serialized into fragment args.
 */
public class ComplexRecipeItemModel implements Serializable {
    private String id;
    private String title;
    private String image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return AppDefines.BASE_IMAGE_URL + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}