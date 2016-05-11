package com.recipeapp.plip.plipapp.adapter.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.SearchHistoryModel;

/**
 * A class to bind RecipeItemModel date to item_recipe layouts for display as items in a RecyclerView.
 * Note that the ViewHolder implements the View.OnClickListener interface, allowing it's subelements
 * to respond to touch events in the view.
 */
public class SearchHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    // Create local instances of the layout elements in item_recipe
    private ImageView recipeThumbnail;
    private TextView recipeName;
    private SearchHistoryModel item;
    private OnRecipeItemClicked onRecipeItemClicked;


    public SearchHistoryViewHolder(final View itemView) {
        super(itemView);
    }

    // A method to bing a RecipeItemModel to the item_recipe layout
    public final void bind(final SearchHistoryModel item) {
        // assign layout instances to local versions
        this.item = item;
        recipeThumbnail = (ImageView)itemView.findViewById(R.id.recipeThumbnail);
        recipeName = (TextView)itemView.findViewById(R.id.recipeName);


        // Use the Glide library (referenced in Gradle) to preload an image resource for the recipeThumbnail
        Glide.with(itemView.getContext())
                .load(item.getImageUrl())
                .into(recipeThumbnail);

        // Set the value of the recipeName
        recipeName.setText(item.getTitle());

        // Adding click listeners on the image and text of the RecyclerView items to handle touch
        // events
        recipeName.setOnClickListener(this);
        recipeThumbnail.setOnClickListener(this);

    }

    public final void unbind() {
        // For later with Butterknife
    }

    @Override
    public void onClick(View v) {
        if(onRecipeItemClicked != null) {
            onRecipeItemClicked.onClick(item);
        }
    }

    // The setter that allows other classes to create a reference to the listener.
    public void setOnRecipeItemClicked(OnRecipeItemClicked onRecipeItemClicked) {
        this.onRecipeItemClicked = onRecipeItemClicked;
    }

    // An interface is added as an internal implementation in our ViewHolder.  This will allow
    // classes that instantiate a new instance of this ViewHolder to subscribe to this interface
    // and listen for events.
    public interface OnRecipeItemClicked {
        void onClick(SearchHistoryModel item);
    }

}