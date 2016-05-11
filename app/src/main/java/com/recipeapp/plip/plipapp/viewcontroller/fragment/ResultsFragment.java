package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;

/**
 * Created by Dulce Palacios on 4/17/16.
 */
public class ResultsFragment extends Fragment
{
    private RecipeAdapter recipeAdapter;
    private ComplexSearchResultsModel complexSearchResultsModel;
    private static final String COMPLEXSEARCHRESULTSMODEL = "complexSearchResultsModel";
    private OnFragmentEvent onFragmentEvent;

    private RecyclerView recyclerView;


    public ResultsFragment(){

    }


    // Fragments are typically instantiated through a static initializer like the one below. This
    // allows for the bundling of information into args that can be added to the fragment to persist
    // through the fragment's lifecycle.
    public static ResultsFragment newInstance(ComplexSearchResultsModel complexSearchResultsModel) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putSerializable(COMPLEXSEARCHRESULTSMODEL, complexSearchResultsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            complexSearchResultsModel = (ComplexSearchResultsModel)getArguments().getSerializable(COMPLEXSEARCHRESULTSMODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        recipeAdapter = new RecipeAdapter(complexSearchResultsModel.getSearchResults());

        recipeAdapter.setOnItemSelected(new RecipeAdapter.OnItemSelected() {
            @Override
            public void onSelected(ComplexRecipeItemModel results) {
                if (onFragmentEvent != null) {
                    onFragmentEvent.onEvent(results);
                }
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.resultsRecyclerView);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnFragmentEvent(OnFragmentEvent onFragmentEvent) {
        this.onFragmentEvent = onFragmentEvent;
    }

    public interface OnFragmentEvent {
        void onEvent(ComplexRecipeItemModel results);
    }

}
