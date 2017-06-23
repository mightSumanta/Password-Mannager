package com.example.sumanta.pman;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Sumanta on 18-06-2017.
 */

public class EntryRowAttributesAdapter extends ArrayAdapter<EntryRowAttributes> {

    private final Context context;
    private ArrayList<EntryRowAttributes> entryRowAttributes;

    public EntryRowAttributesAdapter(@NonNull Context context, @NonNull ArrayList<EntryRowAttributes> list) {
        super(context, R.layout.row_category_attributes, list);

        this.context = context;
        entryRowAttributes = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        final EntryRowAttributes rowAttributes = entryRowAttributes.get(position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        final int tmpPosition = position;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.row_category_attributes, parent, false);
            holder = new ViewHolder();
            holder.mutableWatcher = new MutableWatcher();
            holder.textViewAttribute = (TextView) convertView.findViewById(R.id.tvCategoryAttributes);
            holder.editTextAttribute = (EditText) convertView.findViewById(R.id.etCategoryAttributs);
            holder.editTextAttribute.addTextChangedListener(holder.mutableWatcher);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mutableWatcher.setmActive(false);
        holder.textViewAttribute.setText(rowAttributes.getEntryName());
        holder.editTextAttribute.setText(rowAttributes.getEntryValue(), TextView.BufferType.EDITABLE);
        holder.mutableWatcher.setmPosition(position);
        holder.mutableWatcher.setmActive(true);

        return convertView;
    }

    public static class ViewHolder {
        public TextView textViewAttribute;
        public EditText editTextAttribute;
        public MutableWatcher mutableWatcher;
    }

    class MutableWatcher implements TextWatcher {

        private int mPosition;
        private boolean mActive;
        private EntryRowAttributes rowAttributes;

        void setmPosition(int position) {
            mPosition = position;
            rowAttributes = entryRowAttributes.get(position);
        }

        void setmActive(boolean active) {
            mActive = active;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mActive) {
                rowAttributes.setEntryValue(s.toString());
            }
        }
    }

}
