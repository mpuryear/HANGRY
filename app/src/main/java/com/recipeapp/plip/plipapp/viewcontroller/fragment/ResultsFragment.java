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

/**
 * Created by Dulce Palacios on 4/17/16.
 */
public class ResultsFragment extends Fragment
{
    private RecipeAdapter recipeAdapter;
    private static final String RECIPEADAPTER = "recipeAdapter";

    private RecyclerView recyclerView;


    public ResultsFragment(){

    }


    // Fragments are typically instantiated through a static initializer like the one below. This
    // allows for the bundling of information into args that can be added to the fragment to persist
    // through the fragment's lifecycle.
    public static ResultsFragment newInstance(RecipeAdapter recipeAdapter) {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        args.putSerializable(RECIPEADAPTER, recipeAdapter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            recipeAdapter = (RecipeAdapter)getArguments().getSerializable(RECIPEADAPTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_results, container, false);
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


}
