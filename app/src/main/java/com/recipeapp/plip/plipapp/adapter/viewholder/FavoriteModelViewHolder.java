package com.recipeapp.plip.plipapp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.FavoriteRecipeModel;

/**
 * Created by Mathew on 5/11/2016.
 */
public class FavoriteModelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Button ingredientName;
    private FavoriteRecipeModel item;
    private OnFavoriteItemClicked onFavoriteItemClicked;

    public FavoriteModelViewHolder(final View itemView) {
        super(itemView);
    }

    // A method to bing a RecipeItemModel to the item_recipe layout
    public final void bind(final FavoriteRecipeModel item) {
        // assign layout instances to local versions
        this.item = item;
        ingredientName = (Button)itemView.findViewById(R.id.ingredientName);
        // Set the value of the recipeName
        ingredientName.setText(item.getTitle());

        // Adding click listeners on the image and text of the RecyclerView items to handle touch
        // events
        ingredientName.setOnClickListener(this);

    }

    public final void unbind() {
        // For later with Butterknife
    }

    @Override
    public void onClick(View v) {
        if(onFavoriteItemClicked != null) {
            onFavoriteItemClicked.onClick(item);
        }
    }

    // The setter that allows other classes to create a reference to the listener.
    public void setOnFavoriteItemClicked(OnFavoriteItemClicked onFavoriteItemClicked) {
        this.onFavoriteItemClicked = onFavoriteItemClicked;
    }

    // An interface is added as an internal implementation in our ViewHolder.  This will allow
    // classes that instantiate a new instance of this ViewHolder to subscribe to this interface
    // and listen for events.
    public interface OnFavoriteItemClicked {
        void onClick(FavoriteRecipeModel item);
    }

}
