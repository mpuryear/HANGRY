package com.recipeapp.plip.plipapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import

import java.util.ArrayList;

/**
 * Created by student on 3/31/16.
 */
public class ItemAdapter extends BaseAdapter {

    // Variables
    private ArrayList<Ingredient> items;
    private LayoutInflater itemInf;


    public ItemAdapter(Context c, ArrayList<Ingredient> theIngredients){
        items   = theIngredients;
        itemInf = LayoutInflater.from(c);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//            LinearLayout itemLay = (LinearLayout)itemInf.inflate(R.layout.item_list, parent, false);

        )
    }
}
