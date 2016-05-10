package com.recipeapp.plip.plipapp.model;

import java.util.ArrayList;

/**
 * Created by student on 5/10/16.
 */
public class RecipeInformationModel {
    private String sourceUrl;
    private ArrayList<ExtendedIngredients> extendedIngredients;
    private String id;
    private String title;
    private String readyInMinutes;

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public ArrayList<ExtendedIngredients> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(ArrayList<ExtendedIngredients> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

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

    public String getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(String readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    class ExtendedIngredients
    {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        String name;
        String amount;
        String unit;

    }
}
