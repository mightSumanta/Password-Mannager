package com.example.sumanta.pman;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sumanta.pman.CategoryList.CategoryAddListHolderFragment;
import com.example.sumanta.pman.CategoryList.ListHolder;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.IndividualData.IndividualDataFragment;
import com.example.sumanta.pman.MainMenuPage.MainMenuActivity;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class NothingHere extends Fragment {


    public NothingHere() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_nothing_here, container, false);

        if (ParentFragmentDetails.isMainMenuNothingHere) {
            ParentFragmentDetails.isMainMenuNothingHere = false;
            ((MainMenuActivity) getActivity()).getSupportActionBar().setTitle("Home");
        } else {
            ((ListHolder) getActivity()).getSupportActionBar().setTitle(ParentFragmentDetails.parentFragmentName);
        }

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fabNothingHere);
        floatingActionButton.bringToFront();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
            if (ParentFragmentDetails.isMainMenuNothingHere) {
                getActivity().startActivity(new Intent(getActivity(), ListHolder.class));
            } else {
                DataMap.currentItemId = 100;
                DataMap.currentCategoryName = ParentFragmentDetails.parentFragmentName;
                DatabaseTools databaseTools = new DatabaseTools(getActivity(), 1);
                databaseTools.getIndividualDataItems(DataMap.currentItemId, DataMap.currentCategoryName);

                ArrayList<EntryRowAttributes> entryRowAttributes = new ArrayList<>();
                EntryRowAttributes rowAttributes = null;
            //        Log.i("DataLisFragment", String.valueOf(itemAttributes.size()));
                for (int i = 0; i < DataMap.CategoryEntryNames.size(); i++) {
                    rowAttributes = new EntryRowAttributes(DataMap.CategoryEntryNames.get(i), "");
                    entryRowAttributes.add(rowAttributes);
                }


            /*Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("rowAttributesArrayList", rowAttributesArrayList);*/

                getActivity().getIntent().putParcelableArrayListExtra("rowAttributesArrayList", entryRowAttributes);

                Fragment fragment = new IndividualDataFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.content_list_holder, fragment, "IndividualDataFragment");
                fragmentTransaction.remove(NothingHere.this);
                fragmentTransaction.commit();
            }

            }
        });

        return root;
    }

}
