package com.recipeapp.plip.plipapp;

//import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by stephanie on 4/21/16.
 */
public class SingleRecipe extends AppCompatActivity {

    Button Link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_recipe);


        Link = (Button) findViewById(R.id.link);

        Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent go = new Intent(SingleRecipe.this, safari.class);
                //startActivity(go);


            }

        });



    }






}
