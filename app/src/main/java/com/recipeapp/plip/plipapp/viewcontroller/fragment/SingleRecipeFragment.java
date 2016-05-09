package com.recipeapp.plip.plipapp.viewcontroller.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.SingleRecipe;

/**
 * Created by student on 5/8/16.
 */
public class SingleRecipeFragment extends Fragment {

    TextView title;
    ScrollView scroll;
    Button link;

//    ArrayAdapter<String> adapter;
//    String [] versions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_singlerecipe, container, false);

//        View view = inflater.inflate(R.layout.fragment_singlerecipe, container, false);
//
//        title = (TextView)view.findViewById(R.id.title);
//        scroll = (ScrollView)view.findViewById(R.id.scroll);
//        link = (Button)view.findViewById(R.id.link);
//
//       // versions = getResources().getStringArray(R.array.android)
//
//        return view;

    }

    public static SingleRecipeFragment newInstance() {
        SingleRecipeFragment fragment = new SingleRecipeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

//    private ImageView singlerecipefragment;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.fragment_singlerecipe);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        View view = inflater.inflate(R.layout.fragment_results, container, false);
//        //singlerecipefragment = (ImageView)view.findViewById(R.id.resultsRecyclerView);
//        return view;
//    }
//

}
