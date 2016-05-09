package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.recipeapp.plip.plipapp.AppDefines;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.IngredientAdapter;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.listener.IRecipeCallbackListener;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.IngredientModel;
import com.recipeapp.plip.plipapp.model.RecipeItemModel;
import com.recipeapp.plip.plipapp.service.RecipeSearchTask;
import com.recipeapp.plip.plipapp.service.api.ApiClient;
import com.recipeapp.plip.plipapp.viewcontroller.activity.SearchActivity;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A fragment containing all of the implementation details for recipe searches that were previously
 * contained in the SearchActivity.  This allows us to change out the current function of SearchActivity
 * without having to create a new Activity.
 */
public class SearchFragment extends Fragment{
    private static final String TAG = SearchFragment.class.getSimpleName();
    private EditText searchText;
    private String ingredient;
    private ArrayList<IngredientModel> ingredientList;
    private IngredientModel newIngredient;
    private Button searchButton;
    private TextView lastSearchedText;
    public RecyclerView recipeRecyclerView;
    private RecyclerView ingredientRecyclerView;
    private RecipeAdapter adapter;
    private IngredientAdapter ingredientAdapter;
    private GridLayoutManager layoutManager;
    private OnFragmentEvent onFragmentEvent;
    private ComplexRecipeItemModel testModel;
    private ArrayList<ComplexRecipeItemModel> testList;
    private Integer scrollDownCounter;
//    public static ArrayList<IngredientModel> publicIngredientList;


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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Assigning layout file instances of these UI elements to their java counterparts
        searchText = (EditText)view.findViewById(R.id.searchText);
        lastSearchedText = (TextView)view.findViewById(R.id.lastSearchedText);
        searchButton = (Button)view.findViewById(R.id.searchButton);
        recipeRecyclerView = (RecyclerView)view.findViewById(R.id.recipeRecyclerView);
        ingredientRecyclerView = (RecyclerView)view.findViewById(R.id.ingredientRecyclerView);

        scrollDownCounter = 0;

        // A RecyclerView needs a layout manager assigned to instruct it on how to lay content out
        layoutManager = new GridLayoutManager(recipeRecyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false);
       // ingredientLayoutManager = new GridLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL, false);
        //Assigning the horizontal layoutManager the search flags at the top
        ingredientRecyclerView.setLayoutManager(new GridLayoutManager(ingredientRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));


        ingredientList = new ArrayList<>();

        // A click listener is defined to handle the callback from the RecipeAsyncTask
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                closeKeyboard(getActivity(), searchText.getWindowToken());

                // Store our string and generate an IngredientModel out of it.
                ingredient = searchText.getText().toString().trim();

                // Empty our search field
                searchText.setText("");
                newIngredient = new IngredientModel();
                newIngredient.setName(ingredient);


                Boolean alreadyStoredInList = false;
                // Store our ingredient model in our ArrayList of ingredients
                for(int i = 0; i < ingredientList.size(); i++)
                {

                    Log.d(TAG, "Printing ingredientList @" + Integer.toString(i) + ingredientList.get(i).getName());
                    if (ingredientList.get(i).getName().equals(ingredient))
                        alreadyStoredInList = true;
                }
                if(!alreadyStoredInList && !ingredient.equals("")) {
                    ingredientList.add(newIngredient);
                }

                ingredientAdapter = new IngredientAdapter(ingredientList);

                // Assigning the search text flags an adapter.
                ingredientRecyclerView.setAdapter(ingredientAdapter);



                String concatenatedSearchParam = "";

//                // Concatenate all of our ingredients and then pass our new string to the recipeSearchTask
                for(int i = 0; i < ingredientList.size(); i++)
                    concatenatedSearchParam += ingredientList.get(i).getName() + " ";

                // Set our textview for last searched text
                lastSearchedText.setText(concatenatedSearchParam);



                ApiClient.getInstance().getRecipeApiAdapter()
                        .getRecipeSearchResults(Integer.toString(scrollDownCounter * AppDefines.SCROLLDOWN_MULTIPLIER),
                                concatenatedSearchParam)
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

                                // On handling the http response, instantiate a new adapter with the results
                                adapter = new RecipeAdapter(complexSearchResultsModel.getSearchResults());

                                adapter.setOnItemSelected(new RecipeAdapter.OnItemSelected() {
                                    @Override
                                    public void onSelected(ComplexRecipeItemModel item) {
                                        if (onFragmentEvent != null) {
                                            onFragmentEvent.onEvent(item);
                                        }
                                    }
                                });

                                // Assigning the LayoutManager to the RecyclerView
                                recipeRecyclerView.setLayoutManager(layoutManager);
                                // Assigning the Adapter to the RecyclerView. If this isn't done, the view will not populate
                                recipeRecyclerView.setAdapter(adapter);

                                // Instantiate a new AsyncTask to make an http call on background thread.
                                // RecipeSearch task now has a constructor which requires an instance of IRecipeCaallbackListener


                            }
                        });
            }
        });

        return view;
    }



    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
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