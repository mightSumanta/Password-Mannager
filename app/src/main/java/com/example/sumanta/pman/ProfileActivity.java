package com.example.sumanta.pman;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sumanta.pman.LoginPage.User;
import com.example.sumanta.pman.MainMenuPage.MainMenuActivity;

public class ProfileActivity extends AppCompatActivity {

    EditText etName;
    EditText etMail;
    EditText etPword;
    EditText etCPword;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setProperties();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenActions();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();

        if (item.getTitle().equals("Update")) {
            onUpdateItemSelected();
        }

        return super.onOptionsItemSelected(item);
    }

    private void onUpdateItemSelected() {
        etName = (EditText) this.findViewById(R.id.etChangeName);
        etMail = (EditText) this.findViewById(R.id.etChangeEmail);
        etPword = (EditText) this.findViewById(R.id.etChangePpassword);
        etCPword = (EditText) this.findViewById(R.id.etChangeConfirmPassword);

        if (validate()) {
            User.name = etName.getText().toString();
            User.mailId = etMail.getText().toString();
            User.passWord = etPword.getText().toString();

            Log.i("onUpdateItemSelected", "ProfileActivity: " + String.valueOf(id));
            DatabaseTools databaseTools = new DatabaseTools(this, 1);
            if (databaseTools.updateUser(id)) {
                Intent intent = new Intent(this, MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error while saving data", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Error while saving data", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean validate() {
        boolean valid = true;

        String name = etName.getText().toString();
        String email = etMail.getText().toString();
        String password = etPword.getText().toString();
        String confirmPpassword = etCPword.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            etName.setError("at least 3 characters");
            valid = false;
        } else {
            etName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etMail.setError("enter a valid email address");
            valid = false;
        } else {
            etMail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPword.setError(null);
        }

        if (!password.equals(confirmPpassword)) {
            etCPword.setError("Passwords Don't match");
            valid = false;
        } else {
            etCPword.setError(null);
        }

        return valid;
    }

    public void setProperties() {

        DatabaseTools databaseTools = new DatabaseTools(this, 1);
        id = databaseTools.getUser(User.passWord);
        Log.i("setProperties", "ProfileActivity: " + String.valueOf(id));

        etName = (EditText) this.findViewById(R.id.etChangeName);
        etMail = (EditText) this.findViewById(R.id.etChangeEmail);
        etPword = (EditText) this.findViewById(R.id.etChangePpassword);
        etCPword = (EditText) this.findViewById(R.id.etChangeConfirmPassword);

        etName.setText(User.name);
        etMail.setText(User.mailId);
        etPword.setText(User.passWord);
    }

    private void screenActions() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
