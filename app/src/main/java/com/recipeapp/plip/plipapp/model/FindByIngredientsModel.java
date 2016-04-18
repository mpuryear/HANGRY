package com.recipeapp.plip.plipapp.model;

/**
 * Created by Dulce Palacios on 4/17/16.
 */
public class FindByIngredientsModel
{
    private Integer id;
    private String title;
    private String image;
    private String imageType;
    private Integer usedIngredientCount;
    private Integer missedIngredientCount;
    private Integer likes;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public Integer getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public Integer getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getId() {

        return id;
    }
}
