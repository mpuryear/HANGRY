package com.recipeapp.plip.plipapp.viewcontroller.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.recipeapp.plip.plipapp.AppDefines;
import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.adapter.IngredientAdapter;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.IngredientModel;
import com.recipeapp.plip.plipapp.service.api.ApiClient;

import java.util.ArrayList;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A fragment containing all of the implementation details for recipe searches that were previously
 * contained in the SearchActivity.  This allows us to change out the current function of SearchActivity
 * without having to create a new Activity.
 */
public class SearchFragment extends Fragment {
    View view;

    private static final String TAG = SearchFragment.class.getSimpleName();
    private EditText searchText;
    private String mealType;
    private String cuisineType;
    private String allergiesType;
    private ArrayList<IngredientModel> ingredientList;
    private IngredientModel newIngredient;
    private Button searchButton;
    private Button addItemButton;
    private Button allergiesButton;
    private Button cuisineButton;
    private Button mealsButton;
    private TextView lastSearchedText;
    private TextView errorMessage;
      private RecyclerView ingredientRecyclerView;
      private IngredientAdapter ingredientAdapter;
      private OnFragmentEvent onFragmentEvent;
    private IngredientAdapter.OnItemSelected onItemSelected;
    private Integer scrollDownCounter;
    private Boolean mealsCalled;
    private Boolean cuisineCalled;
    private Boolean allergiesCalled;
    private Boolean firstOpen = true;
    private String concatenatedSearchParam;





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



        mealType = "";
        cuisineType = "";
        allergiesType = "";
        concatenatedSearchParam = "";
        cuisineCalled = mealsCalled = allergiesCalled = false;


        // Only instantiate our view and ingredients on creating this fragment, not when we recall a past
        // fragment from the backstack
        if(firstOpen) {
            view = inflater.inflate(R.layout.fragment_search, container, false);
            ingredientList = new ArrayList<>();
            firstOpen = false;
          }
        //}
        // Assigning layout file instances of these UI elements to their java counterparts
        searchText = (EditText) view.findViewById(R.id.searchText);
//        lastSearchedText = (TextView) view.findViewById(R.id.lastSearchedText);
        errorMessage = (TextView) view.findViewById(R.id.errorMessage);
        searchButton = (Button) view.findViewById(R.id.searchButton);
        addItemButton = (Button) view.findViewById(R.id.addItemButton);
        allergiesButton = (Button) view.findViewById(R.id.allergiesButton);
        cuisineButton = (Button) view.findViewById(R.id.cuisineButton);
        mealsButton = (Button) view.findViewById(R.id.mealButton);

        ingredientRecyclerView = (RecyclerView) view.findViewById(R.id.ingredientRecyclerView);

        scrollDownCounter = 0;


        // A click listener is defined to handle the callback from the RecipeAsyncTask
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                concatenatedSearchParam = "";
                Toast.makeText(getContext(),"Searching...",Toast.LENGTH_SHORT).show();
                closeKeyboard(getActivity(), searchText.getWindowToken());

                // Store our string and generate an IngredientModel out of it.
                newIngredient = getIngredientFromSearchBox(searchText);


                if (!newIngredient.getName().equals("") && !ingredientList.contains(newIngredient)) {
                    ingredientList.add(newIngredient);
                }

                ingredientAdapter = new IngredientAdapter(ingredientList);
                ingredientAdapter.setOnItemSelected(new IngredientAdapter.OnItemSelected() {
                    @Override
                    public void onSelected(IngredientModel ingredientModel) {
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




               // Concatenate all of our ingredients and then pass our new string to the recipeSearchTask
                for (int i = 0; i < ingredientList.size(); i++)
                    concatenatedSearchParam += ingredientList.get(i).getName().trim() + " ";

// Set our textview for last searched text
//                lastSearchedText.setText(allergiesType.trim() + " " +
//                        cuisineType.trim() + " " +
//                        mealType.trim() + " " +
//                        Integer.toString(scrollDownCounter * AppDefines.SCROLLDOWN_MULTIPLIER)
//                        + " " + concatenatedSearchParam);


                ApiClient.getInstance().getRecipeApiAdapter()
                        .getRecipeSearchResults(
                                allergiesType,
                                cuisineType,
                                mealType,
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

                                // if our search yielded no results, tell the user

                                // if (adapter.getItemCount() == 0) {
                                if(complexSearchResultsModel.getSearchResults().size() == 0) {
                                    Log.d(TAG, "The search returned no results.\n");
                                    errorMessage.setText("Your search yielded no results.");
                                } else {
                                       errorMessage.setText("");
                                    if(onFragmentEvent != null) {
                                        onFragmentEvent.onEvent(complexSearchResultsModel);
                                    }
                                }
                            }
                        });
            }
        });


        addItemButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 closeKeyboard(getActivity(), searchText.getWindowToken());
                                                 newIngredient = getIngredientFromSearchBox(searchText);

                                                 if (!newIngredient.getName().equals("") && !ingredientList.contains(newIngredient)) {
                                                     ingredientList.add(newIngredient);
                                                 }

                                                 ingredientAdapter = new IngredientAdapter(ingredientList);
                                                 ingredientAdapter.setOnItemSelected(new IngredientAdapter.OnItemSelected() {
                                                     @Override
                                                     public void onSelected(IngredientModel ingredientModel) {
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
                allergiesCalled = true;
                cuisineCalled = false;
                mealsCalled = false;
                registerForContextMenu(view);
                view.showContextMenu();
            }
        });

        mealsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealsCalled = true;
                cuisineCalled = false;
                allergiesCalled = false;
                registerForContextMenu(view);
                view.showContextMenu();
            }
        }); // End of mealsButton onclickListener


        cuisineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuisineCalled = true;
                mealsCalled = false;
                allergiesCalled = false;
                registerForContextMenu(view);
                view.showContextMenu();
            }
        }); // End of cuisineButton onclickListener


        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        // See what class called our context menu and handle it accordingly.

        if (mealsCalled)
            menuInflater.inflate(R.menu.meals_menu, menu);
        else if (cuisineCalled)
            menuInflater.inflate(R.menu.cuisene_menu, menu);
        else if (allergiesCalled)
            menuInflater.inflate(R.menu.allergies_menu, menu);

        // Create all of our menu items for our checkbox
        MenuItem item_one;
        MenuItem item_two;
        MenuItem item_three;
        MenuItem item_four;
        MenuItem item_five;
        MenuItem item_six;
        MenuItem item_seven;
        MenuItem item_eight;
        MenuItem item_nine;
        MenuItem item_ten;
        MenuItem item_eleven;
        MenuItem item_twelve;
        MenuItem item_thirteen;

        if (mealsCalled) {
            item_one = (MenuItem) view.findViewById(R.id.mainCourseButton);
            item_two = (MenuItem) view.findViewById(R.id.sideDishButton);
            item_three = (MenuItem) view.findViewById(R.id.dessertButton);
            item_four = (MenuItem) view.findViewById(R.id.appetizerButton);
            item_five = (MenuItem) view.findViewById(R.id.saladButton);
            item_six = (MenuItem) view.findViewById(R.id.breakfastButton);
            item_seven = (MenuItem) view.findViewById(R.id.beverageButton);
            item_eight = (MenuItem) view.findViewById(R.id.none);

            menu.setHeaderTitle("Meals");
        } else if (cuisineCalled) {
            item_one = (MenuItem) view.findViewById(R.id.africanCuisine);
            item_two = (MenuItem) view.findViewById(R.id.chineseCuisine);
            item_three = (MenuItem) view.findViewById(R.id.japaneseCuisine);
            item_four = (MenuItem) view.findViewById(R.id.indianCuisine);
            item_five = (MenuItem) view.findViewById(R.id.frenchCuisine);
            item_six = (MenuItem) view.findViewById(R.id.italianCuisine);
            item_seven = (MenuItem) view.findViewById(R.id.mexicanCuisine);
            item_eight = (MenuItem) view.findViewById(R.id.middleEasternCuisine);
            item_nine = (MenuItem) view.findViewById(R.id.jewishCuisine);
            item_ten = (MenuItem) view.findViewById(R.id.americanCuisine);
            item_eleven = (MenuItem) view.findViewById(R.id.southernCuisine);
            item_twelve = (MenuItem) view.findViewById(R.id.caribbeanCuisine);
            item_thirteen = (MenuItem) view.findViewById(R.id.none);
            menu.setHeaderTitle("Cuisines");
        } else if (allergiesCalled) {
            item_one = (MenuItem) view.findViewById(R.id.id_dairy);
            item_two = (MenuItem) view.findViewById(R.id.id_egg);
            item_three = (MenuItem) view.findViewById(R.id.id_gluten);
            item_four = (MenuItem) view.findViewById(R.id.id_peanut);
            item_five = (MenuItem) view.findViewById(R.id.id_sesame);
            item_six = (MenuItem) view.findViewById(R.id.id_seafood);
            item_seven = (MenuItem) view.findViewById(R.id.id_shellfish);
            item_eight = (MenuItem) view.findViewById(R.id.id_soy);
            item_nine = (MenuItem) view.findViewById(R.id.id_sulfite);
            item_ten = (MenuItem) view.findViewById(R.id.id_treenut);
            item_eleven = (MenuItem) view.findViewById(R.id.id_wheat);
            item_twelve = (MenuItem) view.findViewById(R.id.none);

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        onOptionsItemSelected(item);
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle actionbar clicks here.

        if (mealsCalled) {
            mealsButton.setText(item.getTitle());
            mealType = item.getTitle().toString();
            if (mealType.equals("none")) {
                mealsButton.setText("MEAL");
                mealType = "";
            }
        } else if (cuisineCalled) {
            cuisineButton.setText(item.getTitle());
            cuisineType = item.getTitle().toString();
            if (cuisineType.equals("none"))
            {
                cuisineButton.setText("CUISINE");
                cuisineType = "";
            }
        } else if (allergiesCalled) {
            allergiesButton.setText(item.getTitle());
            allergiesType = item.getTitle().toString();
            if (allergiesType.equals("none")) {
                allergiesButton.setText("ALLERGIES");
                allergiesType = "";
            }
        }


        item.isChecked();
        return true;
    }

    public String getSearchString(){
        return concatenatedSearchParam;
    }

    public String getAllergiesType() { return allergiesType;}

    public String getMealType() { return mealType; }

    public String getCuisineType() { return cuisineType; }

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
        void onEvent(ComplexSearchResultsModel item);
    }

}