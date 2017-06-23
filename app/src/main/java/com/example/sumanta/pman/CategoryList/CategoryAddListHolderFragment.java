package com.example.sumanta.pman.CategoryList;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumanta.pman.DataEntries.DataListFragment;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.DataEntries.Datas;
import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.EntryRowAttributes;
import com.example.sumanta.pman.IndividualData.IndividualDataFragment;
import com.example.sumanta.pman.NothingHere;
import com.example.sumanta.pman.ParentFragmentDetails;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryAddListHolderFragment extends Fragment {

    private ListView listView;
    private ArrayList<Categories> list;
    private DatabaseTools categoryTableDB;
    private ItemListAdapter itemAdapter;
    private AlertDialog dialog;
    private AlertDialog alertDialog;
    private AlertDialog alDialog;

    public CategoryAddListHolderFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category_add_list_holder, container, false);

        ((ListHolder) getActivity()).getSupportActionBar().setTitle("Categories");

        categoryTableDB = new DatabaseTools(getActivity(), 1);
        list = categoryTableDB.getAllCategories();
        itemAdapter = new ItemListAdapter(getActivity(), list);
        listView = (ListView) root.findViewById(R.id.lvThings);
        listView.setAdapter(itemAdapter);

        createDialogBox();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txt);
//                Toast.makeText(getActivity(), textView.getText(), Toast.LENGTH_SHORT).show();
//                listView.setAdapter(null);
//                if (textView.getText().toString().equals("Banking")) {
                setCurrentCategoryDetails(textView.getText().toString());

            }
        };

        AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.txt);
                categoryTableDB = new DatabaseTools(getActivity(), 1);
                DataMap.currentCategoryName = textView.getText().toString();

                Log.i("CategoryAddListHolder", "LongClick: " + DataMap.currentCategoryName);

                ArrayList<Datas> datas = categoryTableDB.getDataNames(DataMap.currentCategoryName);
                if (!categoryTableDB.isPreInstalledCategoryType()) {
                    if (datas != null) {
                        dialog.show();
                    } else {
                        alDialog.show();
                    }
                } else {
                    alertDialog.show();
                }
                return true;
            }
        };

        listView.setOnItemClickListener(itemClickListener);
        listView.setOnItemLongClickListener(itemLongClickListener);

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fabList);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCategoryAddMenu();
            }
        });

        return root;
    }

    private void getCategoryAddMenu() {

        ArrayList<EntryRowAttributes> rowAttributesArrayList = new ArrayList<>();
        EntryRowAttributes rowAttributes = new EntryRowAttributes("Name:", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 1", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 2", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 3", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 4", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 5", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 6", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 7", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 8", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 9", "");
        rowAttributesArrayList.add(rowAttributes);
        rowAttributes = new EntryRowAttributes("Attribute 10", "");
        rowAttributesArrayList.add(rowAttributes);

                /*Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("rowAttributesArrayList", rowAttributesArrayList);*/

        getActivity().getIntent().putParcelableArrayListExtra("rowAttributesArrayList", rowAttributesArrayList);
        DataMap.currentItemId = 50;

        Fragment fragment = new IndividualDataFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_list_holder, fragment, "CategoryAddFragment");
        fragmentTransaction.remove(CategoryAddListHolderFragment.this);
        fragmentTransaction.commit();
    }

    private void refreshThisFragment () {
        Fragment frag = new CategoryAddListHolderFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.content_list_holder, frag, "CategoryAddListHolderFragment");
        ft.remove(this);
        ft.commit();
    }

    private void createDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder.setTitle("Confirm Deletion").setMessage("Category contains Data Entries. Confirm Deletion ?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                categoryTableDB.deleteCategory(getActivity());
                refreshThisFragment();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        dialog = builder.create();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder1.setMessage("PreInstalled Categories cannot be Deleted");

        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        alertDialog = builder1.create();

        AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        builder2.setMessage("Confirm Deletion ?");

        builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                categoryTableDB.deleteCategory(getActivity());
                refreshThisFragment();
            }
        });

        builder2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alDialog = builder2.create();
    }

    private void setCurrentCategoryDetails (String cName) {
        DataMap.currentCategoryName = cName;
        if (cName.equals("Banking")) {
            DataMap.currentCategoryIcon = R.drawable.bank;
        } else if (cName.equals("Email")) {
            DataMap.currentCategoryIcon = R.drawable.mail;
        } else if (cName.equals("Websites")) {
            DataMap.currentCategoryIcon = R.drawable.web;
        } else if (cName.equals("Applications")) {
            DataMap.currentCategoryIcon = R.drawable.appbrand;
        } else if (cName.equals("Cards")) {
            DataMap.currentCategoryIcon = R.drawable.card;
        } else if (cName.equals("Wifi")) {
            DataMap.currentCategoryIcon = R.drawable.wifi;
        } else {
            DataMap.currentCategoryIcon = R.drawable.pen;
        }

        DatabaseTools bankTableDB = new DatabaseTools(getActivity(), 1);
        ArrayList<Datas> list = bankTableDB.getDataNames(cName);
        DataMap.dataMap = list;

        if (list != null) {
            Fragment fragment = new DataListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_list_holder, fragment, "DataListFragment");
            fragmentTransaction.remove(CategoryAddListHolderFragment.this);
            fragmentTransaction.commit();
        } else  {
            Fragment fragment = new NothingHere();
            ParentFragmentDetails.parentFragmentName = cName;
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.content_list_holder, fragment, "NothingHere");
            fragmentTransaction.remove(CategoryAddListHolderFragment.this);
            fragmentTransaction.commit();
        }
//                }
    }

}
