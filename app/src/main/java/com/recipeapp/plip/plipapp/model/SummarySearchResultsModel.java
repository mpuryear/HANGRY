package com.recipeapp.plip.plipapp.model;

import java.io.Serializable;

/**
 * Created by student on 5/10/16.
 */
public class SummarySearchResultsModel implements Serializable {
    private String id;
    private String title;
    private String summary;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
