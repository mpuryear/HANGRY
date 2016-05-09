package com.recipeapp.plip.plipapp;

//import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.recipeapp.plip.plipapp.model.ComplexRecipeItemModel;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.RecipeFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SearchFragment;
import com.recipeapp.plip.plipapp.viewcontroller.fragment.SingleRecipeFragment;

/**
 * Created by stephanie on 4/21/16.
 */
public class SingleRecipe extends AppCompatActivity {

    Button Link;
    ImageView background;

    private SingleRecipeFragment srecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recipe);

        Link = (Button) findViewById(R.id.link);

//        Link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Intent go = new Intent(SingleRecipe.this, safari.class);
                //startActivity(go);

//            }

//        });

        background = (ImageView) findViewById(R.id.recipeBackground);
        background.setImageResource(R.drawable.hangry);

        srecipe = SingleRecipeFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.singleContainer, srecipe)
                .addToBackStack(RecipeFragment.class.getSimpleName())
                .commit();

//        srecipe = SingleRecipeFragment.newInstance();
//
//        SingleRecipeFragment.setOnFragmentEvent(new SearchFragment.OnFragmentEvent() {
//            // The override for the listener interface method
//            @Override
//            public void onEvent(ComplexRecipeItemModel recipe) {
//                // Creation of a new, different fragment instance to replace the instance currently in view
//                srecipe = RecipeFragment.newInstance(recipe);
//                Log.d(TAG, "onOnFragmentEvent");
//
//                // The FragmentManager is used to manage all of our fragment transactions, including
//                // the injection into the view, the reordering of the current fragment in the view
//                // to farther down in the back stack, and th addition of the newly injected fragment
//                // to the backstack. All fragment transactions must be committed to finalize the call.
//                // This transaction is replacing the SearchFragment with the RecipeFragment in the
//                // SearchActivity when the fragment event occurs.
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container, recipeFragment)
//                        .addToBackStack(RecipeFragment.class.getSimpleName())
//                        .commit();
//            }
//        });



    }






}
