package com.example.sumanta.pman.IndividualData;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sumanta.pman.CategoryList.Categories;
import com.example.sumanta.pman.CategoryList.CategoryAddListHolderFragment;
import com.example.sumanta.pman.CategoryList.ListHolder;
import com.example.sumanta.pman.DataEntries.DataListFragment;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.DataEntries.Datas;
import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.EntryRowAttributes;
import com.example.sumanta.pman.EntryRowAttributesAdapter;
import com.example.sumanta.pman.NothingHere;
import com.example.sumanta.pman.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sumanta on 19-06-2017.
 */

public class IndividualDataFragment extends Fragment {

    DatabaseTools dBtools;
    ArrayList<String> etValues;
    ListView listView;
    AlertDialog dialog;

    public IndividualDataFragment() {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_data, container, false);

        if (DataMap.currentItemId != 100 && DataMap.currentItemId != 50) {
            ((ListHolder) getActivity()).getSupportActionBar().setTitle(DataMap.currentItemName);
        } else if (DataMap.currentItemId == 100) {
            ((ListHolder) getActivity()).getSupportActionBar().setTitle("New " + DataMap.currentCategoryName);
        } else if (DataMap.currentItemId == 50) {
            ((ListHolder) getActivity()).getSupportActionBar().setTitle("New Category");
        }

        ArrayList<EntryRowAttributes> rowAttributesArrayList = getActivity().getIntent().getParcelableArrayListExtra("rowAttributesArrayList");
        EntryRowAttributesAdapter rowAttributesAdapter = new EntryRowAttributesAdapter(getActivity(), rowAttributesArrayList);
        listView = (ListView) root.findViewById(R.id.lvAttributes);
        listView.setAdapter(rowAttributesAdapter);

//        Log.i("IndividualDataFragment:", String.valueOf(DataMap.currentItemId));

        return root;
    }

    public void saveData() {
        dBtools = new DatabaseTools(getActivity(), 1);
        etValues = new ArrayList<>();

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            EntryRowAttributes attributes = (EntryRowAttributes) listView.getAdapter().getItem(i);
            try {
                etValues.add(attributes.getEntryValue());
//                Log.i("BankFragment", attributes.getEntryValue());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error: Something Happened", Toast.LENGTH_SHORT).show();
            }
        }

        dBtools.addIndividualData(DataMap.currentCategoryName, etValues);

        Fragment frag = new DataListFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content_list_holder, frag, "DataListFragment");
        ft.remove(IndividualDataFragment.this);
        ft.commit();
    }

    public void deleteData() {
        dBtools = new DatabaseTools(getActivity(), 1);
        dBtools.deleteIndividualData();

        ArrayList<Datas> list = dBtools.getDataNames(DataMap.currentCategoryName);

        if (list != null) {
            Fragment frag = new DataListFragment();
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.content_list_holder, frag, "DataListFragment");
            ft.remove(IndividualDataFragment.this);
            ft.commit();
        } else {
            Fragment fragment = new NothingHere();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_list_holder, fragment, "NothingHere");
            fragmentTransaction.remove(IndividualDataFragment.this);
            fragmentTransaction.commit();
        }
    }

    public void updateData() {
        dBtools = new DatabaseTools(getActivity(), 1);
        etValues = new ArrayList<>();

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            EntryRowAttributes attributes = (EntryRowAttributes) listView.getAdapter().getItem(i);
            try {
                etValues.add(attributes.getEntryValue());
//                Log.i("BankFragment", attributes.getEntryValue());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error: Something Happened", Toast.LENGTH_SHORT).show();
            }
        }

        dBtools.updateIndividualData(DataMap.currentCategoryName, etValues);

        Fragment frag = new DataListFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content_list_holder, frag, "DataListFragment");
        ft.remove(IndividualDataFragment.this);
        ft.commit();
    }

    public void saveCategory() {
        dBtools = new DatabaseTools(getActivity(), 1);
        etValues = new ArrayList<>();

        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            EntryRowAttributes attributes = (EntryRowAttributes) listView.getAdapter().getItem(i);
            try {
                etValues.add(attributes.getEntryValue());
//                Log.i("BankFragment", attributes.getEntryValue());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error: Something Happened", Toast.LENGTH_SHORT).show();
            }
        }

        ArrayList<Categories> list = dBtools.getAllCategories();
        Boolean flag = false;
        Boolean boolFlag = false;

        if (etValues.get(0).replace(" ","").matches(".*[A-Za-z0-9]+.*") && etValues.get(0) != null) {
            for (Categories categories: list) {
                if (etValues.get(0).equals(categories.getcName())) {
                    showCategoryExistAlert();
                    flag = true;
                    break;
                }
            }
        } else {
            showInvalidCategoryNameAlert();
            flag = true;
        }

        for (int i = 1; i < etValues.size(); i++) {
            if (!etValues.get(i).replace(" ","").equals(null) && !etValues.get(i).replace(" ","").equals("")) {
                boolFlag = true;
                break;
            }
        }

        if (!boolFlag) {
            flag = true;
            showMinValueInputAlert();
        }

        if (!flag) {
            dBtools.addCategory(etValues);

            Fragment frag = new CategoryAddListHolderFragment();
            android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.content_list_holder, frag, "CategoryAddListHolderFragment");
            ft.remove(IndividualDataFragment.this);
            ft.commit();
        }
    }

    private void showCategoryExistAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Alert").setMessage("Category already exists");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void showInvalidCategoryNameAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Invalid Category Name").setMessage("Category name can only contain Alphabets, Spaces and Numbers and cannot be left blank");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void showMinValueInputAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Invalid Values").setMessage("You have to put atleast one value");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
