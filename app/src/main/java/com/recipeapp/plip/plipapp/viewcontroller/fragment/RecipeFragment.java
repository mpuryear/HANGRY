package com.recipeapp.plip.plipapp.viewcontroller.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.IngredientModel;
import com.recipeapp.plip.plipapp.model.RecipeInformationModel;
import com.recipeapp.plip.plipapp.model.SummarySearchResultsModel;
import com.recipeapp.plip.plipapp.service.api.ApiClient;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A fragment intended to display the details for a single recipe selected from the search results.
 */
public class RecipeFragment extends Fragment {

    private ComplexRecipeItemModel recipe;
    private static final String RECIPE = "recipe";

    private ImageView recipeThumbnail;
    private TextView recipeName;
    private TextView ingredientNames;
    private TextView ingredientAmounts;
    private TextView recipeSummary;
    private String recipeNameLink;
    private String recipeSourceURL;

    private String concatenatedIngredientNames;
    private String concatenatedIngredientAmounts;
    private String summaryText;

    public RecipeFragment() {
        // Required empty public constructor
    }

    // A RecipeItemModel is passed in during instantiation for addition to the fragment's args
    public static RecipeFragment newInstance(ComplexRecipeItemModel recipe) {
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
            recipe = (ComplexRecipeItemModel)getArguments().getSerializable(RECIPE);
        }



    }

    // Once the fragment's view has been created, the RecipeItemModel data is assigned to the various
    // layout elements.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        concatenatedIngredientAmounts = "";
       concatenatedIngredientNames = "";
        summaryText = "";

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        recipeThumbnail = (ImageView)view.findViewById(R.id.recipeFragmentBackground);
        recipeName = (TextView)view.findViewById(R.id.recipeName);
        ingredientNames = (TextView)view.findViewById(R.id.ingredientNames);
        ingredientAmounts = (TextView)view.findViewById(R.id.ingredientAmounts);
        recipeSummary = (TextView)view.findViewById(R.id.recipeSummary);

        // Use the Glide library (referenced in Gradle) to preload an image resource for the recipeDetailThumbnail
        Glide.with(this).load(recipe.getImage())
                .into(recipeThumbnail);

        recipeName.setClickable(true);
        recipeName.setMovementMethod(LinkMovementMethod.getInstance());




        // We need to make 2 more api calls to correctly get all of our data.

        // Call for our summary
        ApiClient.getInstance().getRecipeApiAdapter()
                .getSummarySearchResults(
                        Integer.parseInt(recipe.getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SummarySearchResultsModel>(){


                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SummarySearchResultsModel summarySearchResultsModel) {
                        summaryText = summarySearchResultsModel.getSummary();

                        // Strip all html from the string
                        summaryText = android.text.Html.fromHtml(summaryText).toString();
                        recipeSummary.setText(summaryText);
                    }
                });



        // Listen for our ingredient information
        ApiClient.getInstance().getRecipeApiAdapter()
                .getRecipeInformationResults(
                        Integer.parseInt(recipe.getId()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecipeInformationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RecipeInformationModel recipeInformationModel) {
                        recipeSourceURL = recipeInformationModel.getSourceUrl();
                        for(int i = 0; i < recipeInformationModel.getExtendedIngredients().size(); i++)
                        {
                            // Add all of our ingredients from the returned ingredient list.
                            concatenatedIngredientNames += recipeInformationModel
                                    .getExtendedIngredients()
                                    .get(i)
                                    .getName();

                            Double temp =  Double.parseDouble(recipeInformationModel
                                    .getExtendedIngredients()
                                    .get(i)
                                    .getAmount());

                            concatenatedIngredientAmounts += temp;

                            concatenatedIngredientAmounts += recipeInformationModel
                                    .getExtendedIngredients()
                                    .get(i)
                                    .getUnit();
                            concatenatedIngredientNames += "\n";
                            concatenatedIngredientAmounts += "\n";

                        }
                        ingredientNames.setText(concatenatedIngredientNames);
                        ingredientAmounts.setText(concatenatedIngredientAmounts);

                        recipeNameLink = "<a href='" + recipeSourceURL + "'>" + recipe.getTitle() + "</a>";
                        recipeName.setText(Html.fromHtml(recipeNameLink));
                    }
                });



        // Set the value of the recipeDetailName
       //recipeName.setText(recipe.getTitle());



        // An inline adapter is declared for the list view since it will only be handling a collection
        // of strings.
//


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