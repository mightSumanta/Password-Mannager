package com.example.sumanta.pman.MainMenuPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumanta.pman.CategoryList.Categories;
import com.example.sumanta.pman.CategoryList.ListHolder;
import com.example.sumanta.pman.DataEntries.DataListFragment;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.DataEntries.Datas;
import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.NothingHere;
import com.example.sumanta.pman.ParentFragmentDetails;
import com.example.sumanta.pman.ProfileActivity;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity{

    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Home");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo_icon_h);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, ListHolder.class));
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        gridView = (GridView) findViewById(R.id.gridview);
        ArrayList<String> lsit = getFilledCategory();
        if (lsit.size() != 0) {
            ParentFragmentDetails.isMainMenuNothingHere = false;
            gridView.setAdapter(new GridItemAdapter(MainMenuActivity.this, lsit));
        } else {
            setUpNothingHerePage();
        }


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView textView = (TextView) v.findViewById(R.id.grid_text);
//                Toast.makeText(MainMenuActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
                setCurrentCategoryDetails(textView.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {

        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, ProfileActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getFilledCategory() {

        ParentFragmentDetails.isFromMainMenu = false;
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Categories> categories;
        DatabaseTools dbTools = new DatabaseTools(this, 1);
        categories = dbTools.getAllCategories();

        for (Categories cat: categories) {
            if (dbTools.getDataNames(cat.getcName()) != null) {
                list.add(cat.getcName());
            }
        }

        if (list != null) {
            return list;
        } else {
            return null;
        }
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

        DatabaseTools bankTableDB = new DatabaseTools(MainMenuActivity.this, 1);
        ArrayList<Datas> list = bankTableDB.getDataNames(cName);
        DataMap.dataMap = list;

        if (list != null) {
            ParentFragmentDetails.isFromMainMenu = true;
            MainMenuActivity.this.startActivity(new Intent(MainMenuActivity.this, ListHolder.class));
        }
    }

    private void setUpNothingHerePage() {
        ParentFragmentDetails.isMainMenuNothingHere = true;
//        Log.i("SetUpNothingHere", "MainMenu: " + String.valueOf(ParentFragmentDetails.isMainMenuNothingHere));
        Fragment fragment = new NothingHere();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contentMain, fragment, "NothingHere");
        fragmentTransaction.commit();
    }
}
