/*
package com.example.sumanta.pman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sumanta.pman.BankCategory.Banks;
import com.example.sumanta.pman.BankCategory.SelectedBankDetails;
import com.example.sumanta.pman.LoginPage.User;
import com.example.sumanta.pman.CategoryList.Categories;

import java.util.ArrayList;

*/
/**
 * Created by Sumanta on 15-06-2017.
 *//*


public class DBtools extends SQLiteOpenHelper {

    public DBtools(Context context, int version) {
        super(context, "myDB", null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        db.execSQL("CREATE TABLE Logins (userId INTEGER PRIMARY KEY, username text, password text)");
        contentValues.put("userId", "1000");
        contentValues.put("username", "abcd");
        contentValues.put("password", "1234");
        db.insert("Logins", null, contentValues);

        contentValues = new ContentValues();
        db.execSQL("CREATE TABLE Categories (cId INTEGER PRIMARY KEY, cName text)");
        contentValues.put("cId", "1000");
        contentValues.put("cName", "Apps");
        db.insert("Categories", null, contentValues);
        db.execSQL("INSERT INTO Categories (cName) VALUES ('Banking')");
        db.execSQL("INSERT INTO Categories (cName) VALUES ('Sites')");

        db.execSQL("CREATE TABLE Banks (cId INTEGER PRIMARY KEY, bankName text, accType1 text, accType2 text, " +
                "accNo text, branch text, ifsc text, micr text)");
        db.execSQL("INSERT INTO Banks (cId, bankName, accType1, accType2, accNo, branch, ifsc, micr) VALUES (1000, 'Hola', '', '', '', '', '', '' )");
        db.execSQL("INSERT INTO Banks (bankName, accType1, accType2, accNo, branch, ifsc, micr) VALUES ('Lol', 'wow', 'dad', 'dfg', 'ht', 'asd', 'dsf' )");
        db.execSQL("INSERT INTO Banks (bankName, accType1, accType2, accNo, branch, ifsc, micr) VALUES ('Wtf', '', '', '', '', '', '' )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public User insertUser(User queryValues) {
        SQLiteDatabase dBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", queryValues.userName);
        contentValues.put("password", queryValues.passWord);
        queryValues.userId = dBase.insert("Logins", null, contentValues);
        dBase.close();
        return queryValues;
    }

    public User getUser(String uName) {
        String query = "SELECT userId, password FROM Logins where username = '" + uName + "'";
        User myUser = new User(0, uName, "");
        SQLiteDatabase dBase = DBtools.this.getReadableDatabase();
        Cursor cursor = dBase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                myUser.userId = cursor.getLong(0);
                myUser.passWord = cursor.getString(1);
            } while (cursor.moveToNext());
        }
        return myUser;
    }

    public void addCategory (String queryValues) {
        SQLiteDatabase dBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cName", queryValues);
        dBase.insert("Categories", null, contentValues);
        dBase.close();
    }

    public ArrayList<Categories> getAllCategories () {
        Categories categories;

        String query = "SELECT cId, cName FROM Categories";
        SQLiteDatabase dBase = DBtools.this.getReadableDatabase();
        Cursor cursor = dBase.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<Categories> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                if (cursor.getString(1).equals("Apps")) {
                    categories = new Categories(cursor.getString(1), R.drawable.appbrand);
                } else if (cursor.getString(1).equals("Banking")) {
                    categories = new Categories(cursor.getString(1), R.drawable.bank);
                } else if (cursor.getString(1).equals("Sites")) {
                    categories = new Categories(cursor.getString(1), R.drawable.ic_menu_gallery);
                } else {
                    categories = new Categories(cursor.getString(1), R.drawable.pen);
                }
                list.add(categories);
            } while (cursor.moveToNext());
        }
        dBase.close();
        return list;
    }

    public void addApps () {

    }

    public ArrayList<Banks> getAllBanks () {
        Banks banks;

        String query = "SELECT cId, bankName, accType1, accType2, accNo, branch, ifsc, micr FROM Banks";
        SQLiteDatabase dBase = DBtools.this.getReadableDatabase();
        Cursor cursor = dBase.rawQuery(query, null);
        cursor.moveToFirst();
        ArrayList<Banks> list = null;
        if (cursor.moveToFirst()) {
            list = new ArrayList<>();
            do {
                banks = new Banks(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)
                        , cursor.getString(5), cursor.getString(6), cursor.getString(7));
                list.add(banks);
            } while (cursor.moveToNext());
        }
        dBase.close();
        return list;
    }

    public void addBanks(ArrayList<String> list) {
        SQLiteDatabase dBase = DBtools.this.getWritableDatabase();
        if (SelectedBankDetails.isOnDatabase) {
            try {
                String[] strings = {String.valueOf(SelectedBankDetails.bankId)};
                dBase.delete("Banks", "cId = ?", strings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("bankName", list.get(0));
        contentValues.put("accType1", list.get(1));
        contentValues.put("accType2", list.get(2));
        contentValues.put("accNo", list.get(3));
        contentValues.put("branch", list.get(4));
        contentValues.put("ifsc", list.get(5));
        contentValues.put("micr", list.get(6));
        dBase.insert("Banks", null, contentValues);
        dBase.close();
    }

    public void deleteBanks() {
        SQLiteDatabase dBase = DBtools.this.getWritableDatabase();
        if (SelectedBankDetails.isOnDatabase) {
            try {
                String[] strings = {String.valueOf(SelectedBankDetails.bankId)};
                dBase.delete("Banks", "cId = ?", strings);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dBase.close();
    }

}
*/
