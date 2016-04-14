package com.recipeapp.plip.plipapp.viewcontroller.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.model.RecipeItemModel;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.RecipeFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchFragment;

/**
 * An Activity controlling the Search feature
 */
public class SearchActivity extends AppCompatActivity {

    // Declaration of the fragments that we will be injecting into our layout
    private SearchFragment searchFragment;
    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Creation of a new fragment instance
        searchFragment = SearchFragment.newInstance();

        // A listener that handles an event from the SearchFragment. In this case, it is handling the
        // selection of an item from the search results RecyclerView
        searchFragment.setOnFragmentEvent(new SearchFragment.OnFragmentEvent() {
            // The override for the listener interface method
            @Override
            public void onEvent(RecipeItemModel recipe) {
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
