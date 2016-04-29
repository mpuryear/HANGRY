package com.recipeapp.plip.plipapp.viewcontroller.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.model.RecipeItemModel;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.RecipeFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.ResultsFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchFragment;

/**
 * An Activity controlling the Search feature
 */
public class SearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Declaration of the fragments that we will be injecting into our layout
    private SearchFragment searchFragment;
    private RecipeFragment recipeFragment;
    private ResultsFragment resultsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Adding toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Creation of a new fragment instance
        searchFragment = SearchFragment.newInstance();

        // A listener that handles an event from the SearchFragment. In this case, it is handling the
        // selection of an item from the search results RecyclerView
        searchFragment.setOnFragmentEvent(new SearchFragment.OnFragmentEvent() {
            // The override for the listener interface method
            @Override
            public void onEvent(ComplexRecipeItemModel recipe) {
                // Creation of a new, different fragment instance to replace the instance currently in view
                recipeFragment = RecipeFragment.newInstance(recipe);

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

        // The FragmentManager is used to inject the SearchFragment into the SearchActivity view.
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, searchFragment)
                .addToBackStack(SearchFragment.class.getSimpleName())
                .commit();

        // Testing Nav Bar:
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_results)
        {
            resultsFragment = ResultsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, resultsFragment)
                    .addToBackStack(ResultsFragment.class.getSimpleName())
                    .commit();
        }
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
