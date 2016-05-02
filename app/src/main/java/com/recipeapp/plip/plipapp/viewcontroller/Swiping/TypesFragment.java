package com.recipeapp.plip.plipapp.viewcontroller.Swiping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.recipeapp.plip.plipapp.R;
import com.recipeapp.plip.plipapp.RecipeGrid;

/**
 * Created by SugarPalaces on 5/1/16.
 */
public class TypesFragment extends Fragment {
    private final String TAG = "TypesFragment";

    // Search Button
    private Button searchButton;

    public TypesFragment() {
        // Required empty public constructor
    }

    public static TypesFragment newInstance() {
        TypesFragment fragment = new TypesFragment();
        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.recipe_types, container, false);
        Log.d(TAG, "onCreateView");
        searchButton = (Button)view.findViewById(R.id.search);

        // onClickListener for the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "search button On click listener initiated");
                Intent intent = new Intent(getActivity(), RecipeGrid.class);
                Log.d(TAG, "Switching to Recipe Grid activity");
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    public void setOnFragmentEvent(OnFragmentEvent onFragmentEvent) {
//        this.onFragmentEvent = onFragmentEvent;
//    }
//
//    public interface OnFragmentEvent {
//        void onEvent()
//    }

}
