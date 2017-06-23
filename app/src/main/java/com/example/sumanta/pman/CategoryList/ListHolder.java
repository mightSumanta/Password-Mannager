package com.example.sumanta.pman.CategoryList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sumanta.pman.DataEntries.DataListFragment;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.DataEntries.Datas;
import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.IndividualData.IndividualDataFragment;
import com.example.sumanta.pman.MainMenuPage.MainMenuActivity;
import com.example.sumanta.pman.NothingHere;
import com.example.sumanta.pman.ParentFragmentDetails;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

public class ListHolder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_holder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarListHolder);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ParentFragmentDetails.isFromMainMenu) {
            Fragment fragment = new DataListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_list_holder, fragment, "DataListFragment").commit();
        } else {
            Fragment fragment = new CategoryAddListHolderFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_list_holder, fragment, "CategoryAddListHolderFragment").commit();
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenActions();
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        prepareMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
//        Toast.makeText(ListHolder.this, item.getTitle(), Toast.LENGTH_SHORT).show();

        if (item.getTitle().equals("Save")) {
            onSaveItemSelected();
        } else if (item.getTitle().equals("Delete")) {
            onDeleteItemSelected();
        } else if (item.getTitle().equals("Update")) {
            onUpdateItemSelected();
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_holder, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        screenActions();
    }

    private void screenActions() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_list_holder);
//                Toast.makeText(ListHolder.this, currentFragment.getTag(), Toast.LENGTH_LONG).show();
        if (currentFragment.getTag().equals("CategoryAddListHolderFragment")) {

            Intent intent = new Intent(ListHolder.this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (currentFragment.getTag().equals("DataListFragment") || currentFragment.getTag().equals("NothingHere") || currentFragment.getTag().equals("CategoryAddFragment")) {

            if (ParentFragmentDetails.isFromMainMenu) {
                Intent intent = new Intent(ListHolder.this, MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Fragment frag = new CategoryAddListHolderFragment();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_list_holder, frag, "CategoryAddListHolderFragment");
                ft.remove(currentFragment);
                ft.commit();
            }
        } else if (currentFragment.getTag().equals("IndividualDataFragment")) {

            DatabaseTools databaseTools = new DatabaseTools(this, 1);
            ArrayList<Datas> list = databaseTools.getDataNames(DataMap.currentCategoryName);

            if (list != null) {
                Fragment frag = new DataListFragment();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_list_holder, frag, "DataListFragment");
                ft.remove(currentFragment);
                ft.commit();
            } else {
                Fragment frag = new CategoryAddListHolderFragment();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_list_holder, frag, "CategoryAddListHolderFragment");
                ft.remove(currentFragment);
                ft.commit();
            }
        } else {

            Toast.makeText(ListHolder.this, currentFragment.getTag() +  "Something Happened", Toast.LENGTH_LONG).show();
        }
    }

    private void prepareMenu(Menu menu) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_list_holder);
//        Toast.makeText(ListHolder.this, currentFragment.getTag(), Toast.LENGTH_LONG).show();
        if (currentFragment.getTag().equals("IndividualDataFragment")) {
            if (DataMap.currentItemId == 100) {
                menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Save");
            } else {
                menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Update");
                menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Delete");
            }

        } else if (currentFragment.getTag().equals("CategoryAddFragment")) {
            menu.add(Menu.NONE, Menu.FIRST, Menu.NONE, "Save");
            menu.add(Menu.NONE, Menu.FIRST + 1, Menu.NONE, "Delete");
        }
    }

    private void onSaveItemSelected() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_list_holder);
//                Toast.makeText(ListHolder.this, currentFragment.getTag(), Toast.LENGTH_LONG).show();
        if (currentFragment.getTag().equals("IndividualDataFragment")) {
            IndividualDataFragment individualDataFragmentFragment = (IndividualDataFragment) currentFragment;
            individualDataFragmentFragment.saveData();
        } else if (currentFragment.getTag().equals("CategoryAddFragment")) {
            IndividualDataFragment individualDataFragmentFragment = (IndividualDataFragment) currentFragment;
            individualDataFragmentFragment.saveCategory();
        } else  {
            Toast.makeText(ListHolder.this, currentFragment.getTag() +  "Something Happened", Toast.LENGTH_LONG).show();
        }
    }

    private void onDeleteItemSelected() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_list_holder);
//                Toast.makeText(ListHolder.this, currentFragment.getTag(), Toast.LENGTH_LONG).show();
        if (currentFragment.getTag().equals("IndividualDataFragment")) {
            IndividualDataFragment fragment = (IndividualDataFragment) currentFragment;
            fragment.deleteData();
        } else if (currentFragment.getTag().equals("CategoryAddFragment")) {
//            IndividualDataFragment individualDataFragmentFragment = (IndividualDataFragment) currentFragment;
//            individualDataFragmentFragment.deleteCategory();
        } else  {
            Toast.makeText(ListHolder.this, currentFragment.getTag() +  "Something Happened", Toast.LENGTH_LONG).show();
        }
    }

    private void onUpdateItemSelected() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_list_holder);
//                Toast.makeText(ListHolder.this, currentFragment.getTag(), Toast.LENGTH_LONG).show();
        if (currentFragment.getTag().equals("IndividualDataFragment")) {
            IndividualDataFragment individualDataFragmentFragment = (IndividualDataFragment) currentFragment;
            individualDataFragmentFragment.updateData();
        } else  {
            Toast.makeText(ListHolder.this, currentFragment.getTag() +  "Something Happened", Toast.LENGTH_LONG).show();
        }
    }

}
