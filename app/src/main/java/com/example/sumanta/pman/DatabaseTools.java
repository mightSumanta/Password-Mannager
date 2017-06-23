package com.example.sumanta.pman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.sumanta.pman.CategoryList.Categories;
import com.example.sumanta.pman.DataEntries.DataMap;
import com.example.sumanta.pman.DataEntries.Datas;
import com.example.sumanta.pman.LoginPage.User;

import java.util.ArrayList;

/**
 * Created by Sumanta on 19-06-2017.
 */

public class DatabaseTools extends SQLiteOpenHelper {
    public DatabaseTools(Context context, int version) {
        super(context, "myDatabase", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Categories (id INTEGER PRIMARY KEY, name TEXT, type TEXT, atr1 TEXT, atr2 TEXT, " +
                "atr3 TEXT, atr4 TEXT, atr5 TEXT, atr6 TEXT, atr7 TEXT, atr8 TEXT, atr9 TEXT, atr10 TEXT, atr11 TEXT)");

        db.execSQL("CREATE TABLE Datas (id INTEGER PRIMARY KEY, categoryname TEXT, name TEXT, username TEXT, password TEXT, accountno TEXT, " +
                "accounttype TEXT, branch TEXT, ifsccode TEXT, micrcode TEXT, nameoncard TEXT, cardtype TEXT, cvv TEXT, " +
                "expdate TEXT, ssid TEXT, notes TEXT)");

        db.execSQL("CREATE TABLE User (userId INTEGER PRIMARY KEY, name TEXT, mailid text, password text)");

        ContentValues cv = new ContentValues();
        cv.put("userId", "1000");
        db.insert("User", null, cv);

        cv = new ContentValues();
        cv.put("id", 100);
        cv.put("name", "Banking");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "Account No");
        cv.put("atr3", "Account Type");
        cv.put("atr4", "Branch");
        cv.put("atr5", "IFSC Code");
        cv.put("atr6", "MICR Code");
        cv.put("atr7", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("name", "Email");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "Username");
        cv.put("atr3", "Password");
        cv.put("atr4", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("name", "Websites");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "Username");
        cv.put("atr3", "Password");
        cv.put("atr4", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("name", "Applications");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "Username");
        cv.put("atr3", "Password");
        cv.put("atr4", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("name", "Cards");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "Name on Card");
        cv.put("atr3", "Card Type");
        cv.put("atr4", "CVV");
        cv.put("atr5", "Exp Date");
        cv.put("atr6", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("name", "Wifi");
        cv.put("type", "Installed");
        cv.put("atr1", "Name");
        cv.put("atr2", "SSID");
        cv.put("atr3", "Username");
        cv.put("atr4", "Password");
        cv.put("atr5", "Notes");
        db.insert("Categories", null, cv);

        cv = new ContentValues();
        cv.put("id", 100);
        db.insert("Datas", null, cv);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Categories> getAllCategories() {

        Categories categories;
        String query = "SELECT name FROM Categories";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<Categories> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                if (cursor.getString(0).equals("Email")) {
                    categories = new Categories(cursor.getString(0), R.drawable.appbrand);
                } else if (cursor.getString(0).equals("Banking")) {
                    categories = new Categories(cursor.getString(0), R.drawable.bank);
                } else if (cursor.getString(0).equals("Sites")) {
                    categories = new Categories(cursor.getString(0), R.drawable.ic_menu_gallery);
                } else {
                    categories = new Categories(cursor.getString(0), R.drawable.pen);
                }
                list.add(categories);
            } while (cursor.moveToNext());
        }
        database.close();
        return list;

    }

    public boolean isPreInstalledCategoryType() {
        String query = "SELECT type FROM Categories WHERE name = '" + DataMap.currentCategoryName + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            if (cursor.getString(0) != null) {
                if (cursor.getString(0).equals("Installed")) {
                    database.close();
                    return true;
                }
            }
        }
        database.close();
        return false;
    }

    public void addCategory(ArrayList<String> list) {

        ContentValues cv = new ContentValues();
        ArrayList<String> categoryAttributes = new ArrayList<>();
        categoryAttributes.add("name");
        categoryAttributes.add("atr1");
        categoryAttributes.add("atr2");
        categoryAttributes.add("atr3");
        categoryAttributes.add("atr4");
        categoryAttributes.add("atr5");
        categoryAttributes.add("atr6");
        categoryAttributes.add("atr7");
        categoryAttributes.add("atr8");
        categoryAttributes.add("atr9");
        categoryAttributes.add("atr10");

        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            if (list.get(i).trim().matches(".*[A-Za-z0-9]+.*")) {
                arrayList.add(list.get(i).trim());
            }
        }

        Boolean flag = false;

        for (String str: arrayList) {
            if (str.replace(" ", "").toLowerCase().equals("name")) {
                flag = true;
                break;
            }
        }

        if (!flag) {
            cv.put(categoryAttributes.get(0), arrayList.get(0));
            cv.put(categoryAttributes.get(1), "Name");
            for (int i = 1; i < arrayList.size(); i++) {
                cv.put(categoryAttributes.get(i+1), arrayList.get(i));
            }
        } else {
            for (int i = 0; i < arrayList.size(); i++) {
                cv.put(categoryAttributes.get(i), arrayList.get(i));
            }
        }

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert("Categories", null, cv);
        database.close();


//        Log.i("DatabaseTools", "addCategory: " + String.valueOf(arrayList.size()));
        updateTable(arrayList);
    }

    public void deleteCategory(Context context) {
//        Toast.makeText(context, DataMap.currentCategoryName, Toast.LENGTH_SHORT).show();
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String[] strings = {DataMap.currentCategoryName};
            database.delete("Datas", "categoryname = ?", strings);
            database.delete("Categories", "name = ?", strings );
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
    }

    public ArrayList<Datas> getDataNames(String categoryName) {

        String query = "SELECT id, name FROM Datas WHERE categoryname = '" + categoryName + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<Datas> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                Datas data = new Datas(cursor.getString(1), cursor.getInt(0));
                list.add(data);
            } while (cursor.moveToNext());
        }
        database.close();
        return list;
    }

    public ArrayList<String> getIndividualDataItems(int id, String categoryName) {

        String queryColumnNames = "";
        String query = "SELECT * FROM Categories WHERE name = '" + categoryName + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<String> categoryValues = null;
        ArrayList<String> categorValueNames = null;
        if (cursor.moveToFirst()) {
            categoryValues = new ArrayList<>();
            categorValueNames = new ArrayList<>();
            do {
                for (int i = 3; i < cursor.getColumnCount(); i++) {
                    if (cursor.getString(i) != null) {
                        categorValueNames.add(cursor.getString(i));
                        String cVal = cursor.getString(i).replace(" ","");
                        cVal = cVal.toLowerCase();
                        categoryValues.add(cVal.trim());
//                        if (!cursor.getString(i).equals("Installed") && !cursor.getString(i).equals("Custom")) {
//                        }
//                        Log.i("DatabaseTools:", cursor.getString(i));
                    } else {
                        break;
                    }
                }
            } while (cursor.moveToNext());
        }
        DataMap.CategoryEntryNames = categorValueNames;

        for (String cVal: categoryValues) {
//            Log.i("DatabaseTools:", cVal);
            queryColumnNames = queryColumnNames + ", " + cVal;
        }

        query = "SELECT id " + queryColumnNames + " FROM Datas WHERE id = '" + id + "'";
//        Log.i("DatabaseTools", query);
        cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
//            Log.i("DatabaseTools", "GetIndividualDataItems: " + String.valueOf(cursor.getColumnCount()));
            categoryValues = new ArrayList<>();
            do {
                categoryValues.clear();
                for (int i = 1; i < cursor.getColumnCount(); i++) {
                    categoryValues.add(cursor.getString(i));
                }
            } while (cursor.moveToNext());
        }

//        Log.i("DatabaseTools", String.valueOf(categoryValues.size()));

        database.close();
        return categoryValues;
    }

    public void addIndividualData(String categoryName, ArrayList<String> list) {
        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM Categories WHERE name = '" + categoryName + "'";
//        Log.i("DatabaseTools:", categoryName);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
//        for (int i = 1; i < cursor.getColumnCount(); i++) {
//            if (cursor.getString(i) != null) {
//                Log.i("GetDataItem", cursor.getString(i));
//            }
//        }
        ArrayList<String> categoryValues = null;
        if (cursor.moveToFirst()) {
            categoryValues = new ArrayList<>();
            do {
                for (int i = 2; i < cursor.getColumnCount(); i++) {
                    if (cursor.getString(i) != null) {
                        Log.i("GetDataItem", cursor.getString(i));
                        if (cursor.getString(i).equals("Installed")) {
                            continue;
                        }
                        String cVal = cursor.getString(i).replace(" ","");
                        cVal = cVal.toLowerCase();
                        categoryValues.add(cVal.trim());
                    }
                }
            } while (cursor.moveToNext());
        }

        cv.put("categoryname", DataMap.currentCategoryName);
        for (int i = 0; i < categoryValues.size(); i++) {
            cv.put(categoryValues.get(i), list.get(i));
            Log.i("DatabaseTools:", "AddDataItem " + categoryValues.get(i) + " " + list.get(i));
        }
        database = this.getWritableDatabase();
        database.insert("Datas", null, cv);
        database.close();
    }

    public void deleteIndividualData() {
        SQLiteDatabase database = this.getWritableDatabase();
        try {
            String[] strings = {String.valueOf(DataMap.currentItemId)};
            database.delete("Datas", "id = ?", strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
    }

    public void updateIndividualData(String categoryName, ArrayList<String> list) {
        deleteIndividualData();
        addIndividualData(categoryName, list);
    }

    public void updateTable(ArrayList<String> atrList) {

        Boolean flag;
        for (String str:atrList) {
//            Log.i("DatabaseTools", "updateTable: Initial values " + str);
        }
        ArrayList<String> cVal = new ArrayList<>();
        String temp = null;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM Datas WHERE id = '" + 100 + "'";
        Cursor cursor = database.rawQuery(query, null);

        for (int j = 0; j < atrList.size(); j++) {
            flag = false;
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if (atrList.get(j) != null) {
                    temp = atrList.get(j).replace(" ","");
                    temp = temp.toLowerCase();
                    if (temp.equals(cursor.getColumnName(i))) {
//                        Log.i("DatabaseTools", "updateTable: " + cVal + " " + cursor.getColumnName(i));
                        flag = true;
                        break;
                    }
                } else {
                    flag = true;
                }
            }
            if (flag){
                continue;
            } else {
                Log.i("UpdateTable:", temp);
                cVal.add(temp);
            }
        }

        for (String values: cVal) {
            database.execSQL("ALTER TABLE Datas ADD COLUMN " + values + " TEXT");
        }
        database.close();
    }

    public Boolean addUser(ArrayList<String> data) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name", data.get(0));
            cv.put("mailid", data.get(1));
            cv.put("password", data.get(2));
            db.insert("User", null, cv);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getUser(String password) {

        String query = "SELECT userId, name, mailid, password FROM User where password = '" + password + "'";
        int uId = 0;
        SQLiteDatabase dBase = this.getReadableDatabase();
        Cursor cursor = dBase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                uId = cursor.getInt(0);
                User.name = cursor.getString(1);
                User.mailId = cursor.getString(2);
                User.passWord = cursor.getString(3);
            } while (cursor.moveToNext());
        }
        dBase.close();
        return uId;
    }

    public Boolean updateUser(int id) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            String[] strings = {String.valueOf(id)};
            database.delete("User", "userId = ?", strings);
            database.close();

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("name", User.name);
            cv.put("mailid", User.mailId);
            cv.put("password", User.passWord);
            db.insert("User", null, cv);
            db.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

