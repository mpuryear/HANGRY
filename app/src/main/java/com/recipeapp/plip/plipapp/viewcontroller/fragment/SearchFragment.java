package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import com.recipeapp.plip.plipapp.AppDefines;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.listener.IRecipeCallbackListener;
import com.recipeapp.plip.plipapp.model.RecipeItemModel;
import com.recipeapp.plip.plipapp.model.SearchResultsModel;
import com.recipeapp.plip.plipapp.service.RecipeSearchTask;
import com.recipeapp.plip.plipapp.service.api.ApiClient;

/**
 * A fragment containing all of the implementation details for recipe searches that were previously
 * contained in the SearchActivity.  This allows us to change out the current function of SearchActivity
 * without having to create a new Activity.
 */
public class SearchFragment extends Fragment {
    private EditText searchText;
    private Button searchButton;
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter adapter;
    private LinearLayoutManager layoutManager;
    private OnFragmentEvent onFragmentEvent;

    public SearchFragment() {
        // Required empty public constructor
    }

    // Fragments are typically instantiated through a static initializer like the one below. This
    // allows for the bundling of information into args that can be added to the fragment to persist
    // through the fragment's lifecycle.
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Assigning layout file instances of these UI elements to their java counterparts
        searchText = (EditText)view.findViewById(R.id.searchText);
        searchButton = (Button)view.findViewById(R.id.searchButton);
        recipeRecyclerView = (RecyclerView)view.findViewById(R.id.recipeRecyclerView);

        // A RecyclerView needs a layout manager assigned to instruct it on how to lay content out
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        // A click listener is defined to handle the callback from the RecipeAsyncTask
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiClient.getInstance().getRecipeApiAdapter()
                        .getRecipeSearchResults(
                                AppDefines.APPLICATION_ID,
                                AppDefines.APPLICATION_KEY,
                                searchText.getText().toString())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<SearchResultsModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(SearchResultsModel searchResultsModel) {

                                // On handling the http response, instantiate a new adapter with the results
                                adapter = new RecipeAdapter(searchResultsModel.getSearchResults());

                                adapter.setOnItemSelected(new RecipeAdapter.OnItemSelected() {
                                    @Override
                                    public void onSelected(RecipeItemModel item) {
                                        if (onFragmentEvent != null) {
                                            onFragmentEvent.onEvent(item);
                                        }
                                    }
                                });

                                // Assigning the LayoutManager to the RecyclerView
                                recipeRecyclerView.setLayoutManager(layoutManager);
                                // Assigning the Adapter to the RecyclerView. If this isn't done, the view will not populate
                                recipeRecyclerView.setAdapter(adapter);
                            }
                        });
            }
        });

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
        void onEvent(RecipeItemModel item);
    }

}