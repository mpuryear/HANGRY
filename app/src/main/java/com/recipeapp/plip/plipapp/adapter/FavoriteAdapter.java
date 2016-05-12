package com.recipeapp.plip.plipapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.viewholder.FavoriteModelViewHolder;
import com.recipeapp.plip.plipapp.model.FavoriteRecipeModel;

import java.util.ArrayList;

/**
 * Created by Mathew on 5/11/2016.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteModelViewHolder> {

    // A local collection that will eventually be assigned a collection of RecipeItemModels as a
    // part of class instantiation
    private ArrayList<FavoriteRecipeModel> recipeItemCollection;
    private OnItemSelected onItemSelected;

    // A constructor requiring a collection of RecipeItemModels
    public FavoriteAdapter(ArrayList<FavoriteRecipeModel> recipeItemCollection){
        this.recipeItemCollection = recipeItemCollection;
    }

    // An overridden virtual method that is responsible for creating an instance of a ViewHolder to
    // contain the data for a given object.
    @Override
    public FavoriteModelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // LayoutInflater is a utility that allows us to assign a view to the local instance of View.
        // We need access to the ViewGroup parent (the base layout in SearchActivty) so that we have
        // the required context to find the item_recipe layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);

        // We return a new instance of RecipeItemViewHolder after passing it a a view to bind data to
        return new FavoriteModelViewHolder(view);
    }

    // An overridden virtual method that binds an item in the collection of RecipesItemModels to the
    // instance of the ViewHolder created in {@see onCreateViewHolder}.
    @Override
    public void onBindViewHolder(FavoriteModelViewHolder holder, int position) {
        // Retrieve a RecipeItemModel from the collection
        FavoriteRecipeModel item = recipeItemCollection.get(position);

        holder.setOnFavoriteItemClicked(new FavoriteModelViewHolder.OnFavoriteItemClicked(){
            @Override
            public void onClick(FavoriteRecipeModel item) {
                if(onItemSelected != null) {
                    onItemSelected.onSelected(item);
                }
            }
        });

        // Bind the ComplexRecipeItemModel data to the view managed by the ViewHolder
        holder.bind(item);
    }

    @Override
    public final void onViewRecycled(final FavoriteModelViewHolder holder) {
        super.onViewRecycled(holder);
        holder.setOnFavoriteItemClicked(null);
        holder.unbind();
    }

    @Override
    public int getItemCount() {
        return recipeItemCollection.size();
    }

    // The setter that allows other classes to create a reference to the listener.
    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    // An interface defined internal to the Adapter class.  This interface, in conjunction with the
    // ViewHolder interface, will relay events captured on the ViewHolder (selection events) up to
    // the Adapter, and subsequently, up to the SearchFragment and SearchActivity by means of a chained
    // set of listeners.
    public interface OnItemSelected {
        void onSelected(FavoriteRecipeModel item);
    }
}

