package com.recipeapp.plip.plipapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.recipeapp.plip.plipapp.viewcontroller.activity.SearchActivity;

import static com.recipeapp.plip.plipapp.AppDefines.SPLASHSCREEN_TIMER_MS;

/**
 * Created by SugarPalaces on 4/20/16.
 */
public class Splashscreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    // Forced time on splash screen in MS
                    sleep(SPLASHSCREEN_TIMER_MS);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(Splashscreen.this, SearchActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
