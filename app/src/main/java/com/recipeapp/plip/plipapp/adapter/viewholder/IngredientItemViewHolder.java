package com.recipeapp.plip.plipapp.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.IngredientModel;


public class IngredientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private Button ingredientName;
    private IngredientModel item;
    private OnIngredientItemClicked onIngredientItemClicked;

    public IngredientItemViewHolder(final View itemView) {
        super(itemView);
    }

    // A method to bing a RecipeItemModel to the item_recipe layout
    public final void bind(final IngredientModel item) {
        // assign layout instances to local versions
        this.item = item;
        ingredientName = (Button)itemView.findViewById(R.id.ingredientName);

        // Set the value of the recipeName
        ingredientName.setText(item.getName());

        // Adding click listeners on the image and text of the RecyclerView items to handle touch
        // events
        ingredientName.setOnClickListener(this);

    }

    public final void unbind() {
        // For later with Butterknife
    }

    @Override
    public void onClick(View v) {
        if(onIngredientItemClicked != null) {
            onIngredientItemClicked.onClick(item);
        }
    }

    // The setter that allows other classes to create a reference to the listener.
    public void setOnIngredientItemClicked(OnIngredientItemClicked onIngredientItemClicked) {
        this.onIngredientItemClicked = onIngredientItemClicked;
    }

    // An interface is added as an internal implementation in our ViewHolder.  This will allow
    // classes that instantiate a new instance of this ViewHolder to subscribe to this interface
    // and listen for events.
    public interface OnIngredientItemClicked {
        void onClick(IngredientModel item);
    }

}
