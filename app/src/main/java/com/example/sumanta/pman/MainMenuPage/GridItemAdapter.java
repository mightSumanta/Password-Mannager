package com.example.sumanta.pman.MainMenuPage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumanta.pman.R;

import java.util.ArrayList;

/**
 * Created by Sumanta on 16-06-2017.
 */

public class GridItemAdapter extends BaseAdapter {

    private Context myContext;
    private ArrayList<String> categoryList;
    private ArrayList<Integer> categoryImages;

    public GridItemAdapter(Context myContext, ArrayList<String> list) {
        this.myContext = myContext;
        categoryList = list;
        setIconToApp();
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater layoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            grid = layoutInflater.inflate(R.layout.grid_item, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);

            textView.setText(categoryList.get(position));
            imageView.setImageResource(categoryImages.get(position));
        } else {
            grid = convertView;
        }

        return grid;

    }

    private void setIconToApp () {
        if (categoryList != null) {
            categoryImages = new ArrayList<>();
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).equals("Banking")) {
                    categoryImages.add(R.drawable.bank);
                } else if (categoryList.get(i).equals("Email")) {
                    categoryImages.add(R.drawable.mail);
                } else if (categoryList.get(i).equals("Websites")) {
                    categoryImages.add(R.drawable.web);
                } else if (categoryList.get(i).equals("Applications")) {
                    categoryImages.add(R.drawable.appbrand);
                } else if (categoryList.get(i).equals("Cards")) {
                    categoryImages.add(R.drawable.card);
                } else if (categoryList.get(i).equals("Wifi")) {
                    categoryImages.add(R.drawable.wifi);
                } else {
                    categoryImages.add(R.drawable.pen);
                }
            }
        }
    }
}