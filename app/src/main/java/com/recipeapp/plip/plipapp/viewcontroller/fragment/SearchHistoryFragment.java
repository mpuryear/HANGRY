package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipeapp.plip.plipapp.AppDefines;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.SearchHistoryAdapter;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.SearchHistoryModel;
import com.recipeapp.plip.plipapp.service.api.ApiClient;

import java.lang.reflect.Array;
import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mathew on 5/11/2016.
 */
public class SearchHistoryFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    View view;
    private OnFragmentEvent onFragmentEvent;
    private ArrayList<SearchHistoryModel> searchHistoryModels;
    private static final String SEARCHHISTORYMODELS = "searchHistory";
    private SearchHistoryAdapter adapter;
    private RecyclerView recyclerView;
    private ComplexSearchResultsModel complexSearchResultsModel;

    // Required
    public SearchHistoryFragment() {

    }

    public static SearchHistoryFragment newInstance(ArrayList<SearchHistoryModel> searchHistoryNew) {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(SEARCHHISTORYMODELS, searchHistoryNew);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            searchHistoryModels = (ArrayList<SearchHistoryModel>) getArguments().getSerializable(SEARCHHISTORYMODELS);
        }

  /*      if(onFragmentEvent != null) {
            onFragmentEvent.onEvent(complexSearchResultsModel);
        }*/

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_history, container, false);
        adapter = new SearchHistoryAdapter(searchHistoryModels);
        adapter.setOnItemSelected(new SearchHistoryAdapter.OnItemSelected() {
            @Override
            public void onSelected(SearchHistoryModel results) {

                // Ideally we would return a model holding all of this info, not just a blank model
                ApiClient.getInstance().getRecipeApiAdapter()
                        .getRecipeSearchResults(
                                results.getAllergies(),
                                results.getCuisine(),
                                results.getMealType(),
                                "0",
                                results.getTitle())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ComplexSearchResultsModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(ComplexSearchResultsModel complexSearchResultsModel) {

                                // if our search yielded no results, tell the user

                                // if (adapter.getItemCount() == 0) {
                                if (complexSearchResultsModel.getSearchResults().size() == 0) {
                                    Log.d(TAG, "The search returned no results.\n");
                                    // errorMessage.setText("Your search yielded no results.");
                                } else {
                                    // errorMessage.setText("");
                                    if (onFragmentEvent != null) {
                                        onFragmentEvent.onEvent(complexSearchResultsModel);
                                    }


                                }
                            }
                        });
            }
        });

        recyclerView = (RecyclerView)view.findViewById(R.id.resultsRecyclerView);
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
        void onEvent(ComplexSearchResultsModel item);
    }
}
