package com.example.sumanta.pman.CategoryList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

/**
 * Created by Sumanta on 16-06-2017.
 */

public class ItemListAdapter extends ArrayAdapter<Categories> {
    private final Context context;
//    private final String[] itemName;
//    private final Integer[] itemLogo;
    private ArrayList<Categories> categoriesArrayList;

    public ItemListAdapter(@NonNull Context context, ArrayList<Categories> list) {
        super(context, R.layout.listview_row, list);
        this.context = context;
        categoriesArrayList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row_root = layoutInflater.inflate(R.layout.listview_row, parent, false);

        TextView textView = (TextView) row_root.findViewById(R.id.txt);
        ImageView imageView = (ImageView) row_root.findViewById(R.id.img);
        Categories categories = categoriesArrayList.get(position);

        textView.setText(categories.getcName());
        imageView.setImageResource(setCurrentCategoryDetails(categories.getcName()));

        return row_root;
    }

    private Integer setCurrentCategoryDetails (String cName) {
        DataMap.currentCategoryName = cName;
        if (cName.equals("Banking")) {
            return R.drawable.bank;
        } else if (cName.equals("Email")) {
            return R.drawable.mail;
        } else if (cName.equals("Websites")) {
            return R.drawable.web;
        } else if (cName.equals("Applications")) {
            return R.drawable.appbrand;
        } else if (cName.equals("Cards")) {
            return R.drawable.card;
        } else if (cName.equals("Wifi")) {
            return R.drawable.wifi;
        } else {
            return R.drawable.pen;
        }
    }
}
