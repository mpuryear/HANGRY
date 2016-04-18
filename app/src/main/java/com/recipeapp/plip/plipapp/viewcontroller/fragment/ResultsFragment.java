package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipeapp.plip.plipapp.R;

/**
 * Created by Dulce Palacios on 4/17/16.
 */
public class ResultsFragment extends Fragment
{
    private RecyclerView resultsRecyclerView;

    public ResultsFragment(){

    }

    // Fragments are typically instantiated through a static initializer like the one below. This
    // allows for the bundling of information into args that can be added to the fragment to persist
    // through the fragment's lifecycle.
    public static ResultsFragment newInstance() {
        ResultsFragment fragment = new ResultsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        resultsRecyclerView = (RecyclerView)view.findViewById(R.id.resultsRecyclerView);
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
