package com.recipeapp.plip.plipapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by student on 4/27/16.
 */
//public class RecipeTypes {
//}



public class RecipeTypes extends AppCompatActivity {

    private final String TAG = "TKT";
    private RelativeLayout mParentLayout = null;
    //int counter;
    Button searchbutton;
    //TextView Q;

    //RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_types);


        //counter = 0;
        searchbutton = (Button) findViewById(R.id.search);
        //box2 = (Button) findViewById(R.id.recipe2);
        //box3 = (Button) findViewById(R.id.recipe3);
        //background = (RelativeLayout) findViewById(R.id.background);

        //Q = (TextView)findViewById(R.id.textView);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(RecipeTypes.this, RecipeGrid.class);
                startActivity(go);


            }
        });



/*
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
*/

        Log.d(TAG, "onCreate");

    }
//
//    @Override
//    protected void onStart(){
//        super.onStart();
//        Log.d(TAG, "onStart");
//    }
//
//
//    @Override
//    protected void onRestart(){
//        super.onRestart();
//        Log.d(TAG, "onRestart");
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        Log.d(TAG, "onResume");
//    }
//
//    @Override
//    protected void onPause(){
//        super.onPause();
//        Log.d(TAG, "onPause");
//    }
//
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//        Log.d(TAG, "onStop");
//    }
//
//
//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        Log.d(TAG, "onDestroy");
//    }


}
