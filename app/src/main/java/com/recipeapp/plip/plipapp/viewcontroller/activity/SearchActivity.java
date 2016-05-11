package com.recipeapp.plip.plipapp.viewcontroller.activity;

import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import android.widget.Toast;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.ComplexSearchResultsModel;
import com.recipeapp.plip.plipapp.model.SearchHistoryModel;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.RecipeFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.ResultsFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchHistoryFragment;

import java.util.ArrayList;

/**
 * An Activity controlling the Search feature
 */
public class SearchActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declaration of the fragments that we will be injecting into our layout
    private SearchFragment searchFragment;
    private RecipeFragment recipeFragment;
    private ResultsFragment resultsFragment;
    private SearchHistoryFragment searchHistoryFragment;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout menuDrawer;
    private ListView menuList;
    private NavigationView navigationView;
    private ArrayList<SearchHistoryModel> searchHistoryModels;
    private SearchHistoryModel searchHistoryModel;

    // ToDO: count as the user scrolls to the bottom of the recycler view and repopulate
    private int timesCalled;




    private final String TAG = "TKT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        timesCalled = 0;
        searchHistoryModels = new ArrayList<>();

        // Creation of a new fragment instance
        searchFragment = SearchFragment.newInstance();
        searchHistoryFragment = SearchHistoryFragment.newInstance(null);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        menuDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  menuList = (ListView) findViewById(R.id.nav_view);



        // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);




        // A listener that handles an event from the SearchFragment. In this case, it is handling the
        // selection of an item from the search results RecyclerView
        searchFragment.setOnFragmentEvent(new SearchFragment.OnFragmentEvent() {
            // The override for the listener interface method
            @Override
            public void onEvent(ComplexSearchResultsModel results) {
                // Creation of a new, different fragment instance to replace the instance currently in view
                resultsFragment = ResultsFragment.newInstance(results);

                // Only add our search history item if the user input a search string.
                if(!searchFragment.getSearchString().equals("")) {
                    searchHistoryModel = new SearchHistoryModel();
                    searchHistoryModel.setTitle(searchFragment.getSearchString());
                    searchHistoryModel.setAllergies(searchFragment.getAllergiesType());
                    searchHistoryModel.setCuisine(searchFragment.getCuisineType());
                    searchHistoryModel.setMealType(searchFragment.getMealType());
                    if (results != null)
                        searchHistoryModel.setImageUrl(results.getSearchResults().get(0).getImage());
                    searchHistoryModels.add(searchHistoryModel);
                }
                Log.d(TAG, "onOnFragmentEvent");





                resultsFragment.setOnFragmentEvent(new ResultsFragment.OnFragmentEvent() {

                    @Override
                    public void onEvent(ComplexRecipeItemModel recipe) {
                        recipeFragment = RecipeFragment.newInstance(recipe);

                        toolbar.setTitle(recipe.getTitle());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, recipeFragment)
                                .addToBackStack(RecipeFragment.class.getSimpleName())
                                .commit();
                    }
                });

                // The FragmentManager is used to manage all of our fragment transactions, including
                // the injection into the view, the reordering of the current fragment in the view
                // to farther down in the back stack, and th addition of the newly injected fragment
                // to the backstack. All fragment transactions must be committed to finalize the call.
                // This transaction is replacing the SearchFragment with the RecipeFragment in the
                // SearchActivity when the fragment event occurs.
                toolbar.setTitle("Search Results");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, resultsFragment)
                        .addToBackStack(RecipeFragment.class.getSimpleName())
                        .commit();
            }
        });




        toolbar.setTitle("Hangry");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, searchFragment)
                .addToBackStack(SearchFragment.class.getSimpleName())
                .commit();


        // Testing Nav Bar:

        toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
             //   invalidateOptionsMenu();

            }


            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
               view.bringToFront();
                //invalidateOptionsMenu();
            }


        };

        toggle.syncState();
        menuDrawer.setDrawerListener(toggle);







        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

            }
        });
       navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(MenuItem item) {
               if(item.getTitle().equals("Search History")) {

                   Fragment fragment = getSupportFragmentManager().findFragmentByTag("searchHistoryFragment");
                   if(fragment != null)
                       getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                   fragment = getSupportFragmentManager().findFragmentByTag("resultsFragment");
                   if(fragment != null)
                       getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                   fragment = getSupportFragmentManager().findFragmentByTag("recipeFragment");
                   if(fragment != null)
                       getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                   searchHistoryFragment = SearchHistoryFragment.newInstance(searchHistoryModels);
                   getSupportFragmentManager().beginTransaction()
                           .replace(R.id.container, searchHistoryFragment)
                           .addToBackStack(SearchHistoryFragment.class.getSimpleName())
                           .commit();
                   toolbar.setTitle("Search History");
                   menuDrawer.closeDrawers();

                   searchHistoryFragment.setOnFragmentEvent(new SearchHistoryFragment.OnFragmentEvent()
                   {



                       @Override
                       public void onEvent(ComplexSearchResultsModel results)
                       {
                           resultsFragment = ResultsFragment.newInstance(results);


                           resultsFragment.setOnFragmentEvent(new ResultsFragment.OnFragmentEvent() {

                               @Override
                               public void onEvent(ComplexRecipeItemModel recipe) {
                                   recipeFragment = RecipeFragment.newInstance(recipe);

                                   toolbar.setTitle(recipe.getTitle());
                                   getSupportFragmentManager().beginTransaction()
                                           .replace(R.id.container, recipeFragment)
                                           .addToBackStack(RecipeFragment.class.getSimpleName())
                                           .commit();
                               }
                           });


                           // The FragmentManager is used to manage all of our fragment transactions, including
                           // the injection into the view, the reordering of the current fragment in the view
                           // to farther down in the back stack, and th addition of the newly injected fragment
                           // to the backstack. All fragment transactions must be committed to finalize the call.
                           // This transaction is replacing the SearchFragment with the RecipeFragment in the
                           // SearchActivity when the fragment event occurs.
                           toolbar.setTitle("Search Results");
                           getSupportFragmentManager().beginTransaction()
                                   .replace(R.id.container, resultsFragment)
                                   .addToBackStack(RecipeFragment.class.getSimpleName())
                                   .commit();
                       }



                   });

                   return true;
               }
               return false;
           }
       });

//         Navigation button for results
//        onNavigationItemSelected();

    }


    @Override
    public void onBackPressed() {


        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish(); // exit the program, dont delete our first fragment
        } else {

            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        //if(menuDrawer.onOptionsItemSelected(menuItem))
        //  return true;
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Toast.makeText(getBaseContext(), "Selected a navigation item", Toast.LENGTH_LONG).show();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inventory) {
            // Recipe Types
            Log.d(TAG, "inventory was clicked");
        } else if (id == R.id.nav_history) {
            Log.d(TAG, "History was clicked");
        } else if (id == R.id.nav_results) {
            Log.d(TAG, "GOT IN HERE - trying to go to the results page");
        }


        //   menuDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

}