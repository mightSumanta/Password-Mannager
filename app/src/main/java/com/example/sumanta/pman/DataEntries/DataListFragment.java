package com.example.sumanta.pman.DataEntries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sumanta.pman.CategoryList.ListHolder;
import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.EntryRowAttributes;
import com.example.sumanta.pman.IndividualData.IndividualDataFragment;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

/**
 * Created by Sumanta on 19-06-2017.
 */

public class DataListFragment extends Fragment {

    ArrayList<Datas> list;
    ListView listViewThingy;

    public DataListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_category_add_list_holder, container, false);

        ((ListHolder) getActivity()).getSupportActionBar().setTitle(DataMap.currentCategoryName);

        DatabaseTools databaseTools = new DatabaseTools(getActivity(), 1);
        list = databaseTools.getDataNames(DataMap.currentCategoryName);

        if (list != null) {
            DataListAdapter adapter = new DataListAdapter(getActivity(), list);
            listViewThingy = (ListView) root.findViewById(R.id.lvThings);
            listViewThingy.setAdapter(adapter);
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Toast.makeText(getActivity(), String.valueOf(list.get((int) id).getDataId()) + " " + list.get((int) id).getDataName(), Toast.LENGTH_LONG).show();
                DataMap.currentItemId = list.get((int) id).getDataId();
                DataMap.currentItemName = list.get((int) id).getDataName();

                DatabaseTools databaseTools = new DatabaseTools(getActivity(), 1);
                ArrayList<String> itemAttributes = databaseTools.getIndividualDataItems(DataMap.currentItemId, DataMap.currentCategoryName);

                ArrayList<EntryRowAttributes> entryRowAttributes = new ArrayList<>();
                EntryRowAttributes rowAttributes = null;
//        Log.i("DataLisFragment", String.valueOf(itemAttributes.size()));
                for (int i = 0; i < itemAttributes.size(); i++) {
                    rowAttributes = new EntryRowAttributes(DataMap.CategoryEntryNames.get(i), itemAttributes.get(i));
                    entryRowAttributes.add(rowAttributes);
                }

                createAtteibuteList(entryRowAttributes);

            }
        };

        FloatingActionButton floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fabList);
        floatingActionButton.show();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataMap.currentItemId = 100;
                DatabaseTools databaseTools = new DatabaseTools(getActivity(), 1);
                databaseTools.getIndividualDataItems(DataMap.currentItemId, DataMap.currentCategoryName);

                ArrayList<EntryRowAttributes> entryRowAttributes = new ArrayList<>();
                EntryRowAttributes rowAttributes = null;
//        Log.i("DataLisFragment", String.valueOf(itemAttributes.size()));
                for (int i = 0; i < DataMap.CategoryEntryNames.size(); i++) {
                    rowAttributes = new EntryRowAttributes(DataMap.CategoryEntryNames.get(i), "");
                    entryRowAttributes.add(rowAttributes);
                }

                createAtteibuteList(entryRowAttributes);
            }
        });

        listViewThingy.setOnItemClickListener(itemClickListener);

        return root;
    }

    private void createAtteibuteList(ArrayList<EntryRowAttributes> attributes) {

                /*Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("rowAttributesArrayList", rowAttributesArrayList);*/

        getActivity().getIntent().putParcelableArrayListExtra("rowAttributesArrayList", attributes);

        Fragment fragment = new IndividualDataFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content_list_holder, fragment, "IndividualDataFragment");
        fragmentTransaction.remove(DataListFragment.this);
        fragmentTransaction.commit();
    }
}
