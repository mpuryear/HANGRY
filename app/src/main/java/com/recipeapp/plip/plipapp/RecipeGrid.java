package com.recipeapp.plip.plipapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.RelativeLayout;

public class RecipeGrid extends AppCompatActivity {

    private final String TAG = "TKT";
    //int counter;
    Button box1;
    ImageView box2;
    TextView Q;

    //RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_grid);


        //counter = 0;
        box1 = (Button) findViewById(R.id.recipe1);
        box2 = (ImageView) findViewById(R.id.recipe2);
        //box3 = (Button) findViewById(R.id.recipe3);
        //background = (RelativeLayout) findViewById(R.id.background);

        Q = (TextView)findViewById(R.id.textView);

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RecipeGrid.this, SingleRecipe.class);
                startActivity(go);


            }
        });


        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RecipeGrid.this, SingleRecipe.class);
                startActivity(go);

            }
        });

/*
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
*/

        Log.d(TAG, "onCreate");

    }

    /*

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart");
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause");
    }


    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

*/

}
