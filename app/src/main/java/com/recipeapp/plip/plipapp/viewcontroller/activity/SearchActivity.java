package com.recipeapp.plip.plipapp.viewcontroller.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.RecipeGrid;
import com.recipeapp.plip.plipapp.RecipeTypes;
import com.recipeapp.plip.plipapp.adapter.RecipeAdapter;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.viewcontroller.Swiping.TypesFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.RecipeFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.ResultsFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * An Activity controlling the Search feature
 */
public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declaration of the fragments that we will be injecting into our layout
    private SearchFragment searchFragment;
    private RecipeFragment recipeFragment;
    private ResultsFragment resultsFragment;
    private DrawerLayout menuDrawer;
    private ActionBarDrawerToggle toggle;
//    private TypesFragment typesFragment;r

    private PagerAdapter mPagerAdapter;

    private final String TAG = "TKT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Adding toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Log.d(TAG, "onCreate");


//        ((ActionBarActivity)getApplicationContext()).setSupportActionBar(toolbar);

        // Creation of a new fragment instance
        searchFragment = SearchFragment.newInstance();
        menuDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        // Allows swiping left and right with typesFragment and SearchFragment
        this.initializePaging();
        // A listener that handles an event from the SearchFragment. In this case, it is handling the
        // selection of an item from the search results RecyclerView
        searchFragment.setOnFragmentEvent(new SearchFragment.OnFragmentEvent() {
            // The override for the listener interface method
            @Override
            public void onEvent(ComplexRecipeItemModel recipe) {
                // Creation of a new, different fragment instance to replace the instance currently in view
                recipeFragment = RecipeFragment.newInstance(recipe);
                Log.d(TAG, "onOnFragmentEvent");

                // The FragmentManager is used to manage all of our fragment transactions, including
                // the injection into the view, the reordering of the current fragment in the view
                // to farther down in the back stack, and th addition of the newly injected fragment
                // to the backstack. All fragment transactions must be committed to finalize the call.
                // This transaction is replacing the SearchFragment with the RecipeFragment in the
                // SearchActivity when the fragment event occurs.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, recipeFragment)
                        .addToBackStack(RecipeFragment.class.getSimpleName())
                       .commit();
            }
        });
        // Testing Nav Bar:
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, menuDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    invalidateOptionsMenu();

                }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }


        };
        toggle.syncState();
        menuDrawer.setDrawerListener(toggle);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//         Navigation button for results
//        onNavigationItemSelected();

    }

    // Start of ViewPagerFragmentActivity Code
    private void initializePaging() {
        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, SearchFragment.class.getName()));
//        fragments.add(Fragment.instantiate(this, RecipeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, TypesFragment.class.getName()));
        this.mPagerAdapter  = new com.recipeapp.plip.plipapp.viewcontroller.Swiping.PagerAdapter(super.getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);
    }
    // End of ViewPageFragmentActivity Code

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        } else if (id == R.id.nav_results)
        {
            Log.d(TAG, "GOT IN HERE - trying to go to the results page");
//            Intent intent = new Intent(this, RecipeGrid.class);
//            startActivity(intent);
//            resultsFragment = ResultsFragment.newInstance();
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, resultsFragment)
//                    .addToBackStack(ResultsFragment.class.getSimpleName())
//                    .commit();
        }
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        menuDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}