package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.Menu;

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

import java.lang.reflect.Array;
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
    private String allergies;
    private ArrayList<IngredientModel> ingredientList;
    private IngredientModel newIngredient;
    private Button searchButton;
    private Button addItemButton;
    private Button allergiesButton;
    private Button cuisineButton;
    private Button mealsButton;
    private TextView lastSearchedText;
    public RecyclerView recipeRecyclerView;
    private RecyclerView ingredientRecyclerView;
    private RecipeAdapter adapter;
    private IngredientAdapter ingredientAdapter;
    private GridLayoutManager layoutManager;
    private OnFragmentEvent onFragmentEvent;
    private IngredientAdapter.OnItemSelected onItemSelected;
    private Integer scrollDownCounter;

    // All of our allergies for our popup button and api call
    private  boolean [] allergiesBooleans;


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

        allergies = "";

        // set all of our allergies to false.
        allergiesBooleans = new boolean[12];
        for(int i =0; i < Array.getLength(allergiesBooleans); i++)
            allergiesBooleans[i] = false;

//        Log.d(TAG, "onCreateView");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Assigning layout file instances of these UI elements to their java counterparts
        searchText = (EditText)view.findViewById(R.id.searchText);
        lastSearchedText = (TextView)view.findViewById(R.id.lastSearchedText);
        searchButton = (Button)view.findViewById(R.id.searchButton);
        addItemButton = (Button)view.findViewById(R.id.addItemButton);
        allergiesButton = (Button)view.findViewById(R.id.allergiesButton);
        cuisineButton = (Button)view.findViewById(R.id.cuisineButton);
        mealsButton = (Button)view.findViewById(R.id.mealButton);


       // recipeRecyclerView = (RecyclerView)view.findViewById(R.id.recipeRecyclerView);
        ingredientRecyclerView = (RecyclerView)view.findViewById(R.id.ingredientRecyclerView);

        scrollDownCounter = 0;

        // A RecyclerView needs a layout manager assigned to instruct it on how to lay content out
       // layoutManager = new GridLayoutManager(recipeRecyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false);




        ingredientList = new ArrayList<>();


        // A click listener is defined to handle the callback from the RecipeAsyncTask
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                closeKeyboard(getActivity(), searchText.getWindowToken());

                // Store our string and generate an IngredientModel out of it.
                newIngredient = getIngredientFromSearchBox(searchText);


                if(!newIngredient.getName().equals("") && !ingredientList.contains(newIngredient)) {
                    ingredientList.add(newIngredient);
                }

                ingredientAdapter = new IngredientAdapter(ingredientList);
                ingredientAdapter.setOnItemSelected(new IngredientAdapter.OnItemSelected(){
                    @Override
                    public void onSelected(IngredientModel ingredientModel)
                    {
                        // On selected remove our ingredient model from our list.
                        if (onItemSelected != null) {
                            onItemSelected.onSelected(ingredientModel);
                        }
                        ingredientList.remove(ingredientModel);
                        // Assigning the search text flags an adapter.
                        ingredientRecyclerView.setAdapter(ingredientAdapter);
                        //Assigning the horizontal layoutManager the search flags at the top
                        ingredientRecyclerView.setLayoutManager(new GridLayoutManager(ingredientRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));

                    }
                });

                // Assigning the search text flags an adapter.
                ingredientRecyclerView.setAdapter(ingredientAdapter);
                //Assigning the horizontal layoutManager the search flags at the top
                ingredientRecyclerView.setLayoutManager(new GridLayoutManager(ingredientRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));


                String concatenatedSearchParam = "";

//                // Concatenate all of our ingredients and then pass our new string to the recipeSearchTask
                for(int i = 0; i < ingredientList.size(); i++)
                    concatenatedSearchParam += ingredientList.get(i).getName() + " ";

                // Set our textview for last searched text
                lastSearchedText.setText(AppDefines.BASE_SEARCH_RESPONSE + concatenatedSearchParam);



                ApiClient.getInstance().getRecipeApiAdapter()
                        .getRecipeSearchResults(
                                allergies,
                                Integer.toString(scrollDownCounter * AppDefines.SCROLLDOWN_MULTIPLIER),
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


                          //      recipeRecyclerView.setLayoutManager(layoutManager);

                   //             recipeRecyclerView.setAdapter(adapter);



                            }
                        });
            }
        });



        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard(getActivity(), searchText.getWindowToken());
                newIngredient = getIngredientFromSearchBox(searchText);

                if(!newIngredient.getName().equals("") && !ingredientList.contains(newIngredient)) {
                    ingredientList.add(newIngredient);
                }

                ingredientAdapter = new IngredientAdapter(ingredientList);
                ingredientAdapter.setOnItemSelected(new IngredientAdapter.OnItemSelected(){
                    @Override
                    public void onSelected(IngredientModel ingredientModel)
                    {
                        // On selected remove our ingredient model from our list.
                        if (onItemSelected != null) {
                            onItemSelected.onSelected(ingredientModel);
                        }
                        ingredientList.remove(ingredientModel);
                        // Assigning the search text flags an adapter.
                        ingredientRecyclerView.setAdapter(ingredientAdapter);
                        //Assigning the horizontal layoutManager the search flags at the top
                        ingredientRecyclerView.setLayoutManager(new GridLayoutManager(ingredientRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));

                    }
                });
                // Assigning the search text flags an adapter.
                ingredientRecyclerView.setAdapter(ingredientAdapter);
                ingredientRecyclerView.setLayoutManager(new GridLayoutManager(ingredientRecyclerView.getContext(), 1, GridLayoutManager.HORIZONTAL, false));

            }
        }
        ); // End of addItem.setOnClickListener

        allergiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerForContextMenu(view);
                getActivity().openContextMenu(view);
            }
        });

        return view;
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,view,menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.allergies_menu,menu);
        MenuItem item_dairy = (MenuItem)view.findViewById(R.id.id_dairy);
        MenuItem item_egg = (MenuItem)view.findViewById(R.id.id_egg);



    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        return true;
       // item.setChecked(true);
       //return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle actionbar clicks here.
        return super.onOptionsItemSelected(item);
    }

    public IngredientModel getIngredientFromSearchBox(EditText searchText) {
        // Store our string and generate an IngredientModel out of it.
        String ingredient = searchText.getText().toString().trim();

        // Empty our search field
        searchText.setText("");
        IngredientModel newIngredient = new IngredientModel();
        newIngredient.setName(ingredient);
    return newIngredient;
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