package com.example.sumanta.pman.DataEntries;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sumanta.pman.R;

import java.util.ArrayList;

/**
 * Created by Sumanta on 19-06-2017.
 */

public class DataListAdapter extends ArrayAdapter<Datas> {

    private final Context context;
    private ArrayList<Datas> dataRowList;

    public DataListAdapter(@NonNull Context context, ArrayList<Datas> list) {
        super(context, R.layout.listview_row, list);

        this.context = context;
        dataRowList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row_root = layoutInflater.inflate(R.layout.listview_row, parent, false);

        TextView tvCname = (TextView) row_root.findViewById(R.id.txt);
        ImageView etCname = (ImageView) row_root.findViewById(R.id.img);
        Datas datas = dataRowList.get(position);

        tvCname.setText(datas.getDataName());
        etCname.setImageResource(DataMap.currentCategoryIcon);

        return row_root;
    }
}
