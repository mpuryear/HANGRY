package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.recipeapp.plip.plipapp.Favorites_Database;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.FavoriteAdapter;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.FavoriteRecipeModel;
import com.recipeapp.plip.plipapp.model.RecipeInformationModel;
import com.recipeapp.plip.plipapp.service.api.ApiClient;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mathew on 5/11/2016.
 */
public class FavoritesFragment extends Fragment {

    private OnFragmentEvent onFragmentEvent;
    private static final String COMPLEXSEARCHRESULTSMODEL = "complexSearchResultsModel";
    private RecipeAdapter adapter;
    private RecyclerView recyclerView;
    private ComplexSearchResultsModel complexSearchResultsModel;

    // Required
    public FavoritesFragment() {

    }

    public static FavoritesFragment newInstance(ComplexSearchResultsModel complexSearchResultsModel) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putSerializable(COMPLEXSEARCHRESULTSMODEL, complexSearchResultsModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            complexSearchResultsModel = (ComplexSearchResultsModel)getArguments().getSerializable(COMPLEXSEARCHRESULTSMODEL);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        adapter = new RecipeAdapter(complexSearchResultsModel.getSearchResults());

        adapter.setOnItemSelected(new RecipeAdapter.OnItemSelected() {
            @Override
            public void onSelected(ComplexRecipeItemModel results) {
                if (onFragmentEvent != null)
                    onFragmentEvent.onEvent(results);
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
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


    public void setOnFragmentEvent(OnFragmentEvent onFragmentEvent) {
        this.onFragmentEvent = onFragmentEvent;
    }

    public interface OnFragmentEvent {
        void onEvent(ComplexRecipeItemModel item);
    }
}