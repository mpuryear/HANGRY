package com.recipeapp.plip.plipapp.viewcontroller.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.RecipeItemModel;


/**
 * A fragment intended to display the details for a single recipe selected from the search results.
 */
public class RecipeFragment extends Fragment {

    private RecipeItemModel recipe;
    private static final String RECIPE = "recipe";

    private ImageView recipeDetailThumbnail;
    private TextView recipeDetailName;
    private ListView ingredients;

    public RecipeFragment() {
        // Required empty public constructor
    }

    // A RecipeItemModel is passed in during instantiation for addition to the fragment's args
    public static RecipeFragment newInstance(RecipeItemModel recipe) {
        RecipeFragment fragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            recipe = (RecipeItemModel)getArguments().getSerializable(RECIPE);
        }
    }

    // Once the fragment's view has been created, the RecipeItemModel data is assigned to the various
    // layout elements.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeDetailThumbnail = (ImageView)view.findViewById(R.id.recipeDetailThumbnail);
        recipeDetailName = (TextView)view.findViewById(R.id.recipeDetailName);
        ingredients = (ListView)view.findViewById(R.id.ingredients);


        // Use the Glide library (referenced in Gradle) to preload an image resource for the recipeDetailThumbnail
        Glide.with(this).load(recipe.getSmallImageUrls().get(0))
                .into(recipeDetailThumbnail);

        // Set the value of the recipeDetailName
        recipeDetailName.setText(recipe.getRecipeName());

        // An inline adapter is declared for the list view since it will only be handling a collection
        // of strings.
        ingredients.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, recipe.getIngredients()));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}