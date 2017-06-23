package com.example.sumanta.pman.LoginPage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sumanta.pman.DatabaseTools;
import com.example.sumanta.pman.MainMenuPage.MainMenuActivity;
import com.example.sumanta.pman.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
    private EditText etPword;;
    private Button btnLgn;
//    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPword = (EditText) findViewById(R.id.login_password);
        btnLgn = (Button) findViewById(R.id.btnLogin);

        btnLgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        /*tvNewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.startActivity(new Intent(LoginActivity.this, RegistrationPassword.class));
            }
        });*/
    }

    private void attemptLogin() {
        Log.d(TAG, "Login");

        String password = etPword.getText().toString();
        DatabaseTools dbTools = new DatabaseTools(this, 1);
        dbTools.getUser(password);

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnLgn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 30);
    }


    public void onSignupSuccess() {
        btnLgn.setEnabled(true);
        setResult(RESULT_OK, null);
        // TODO: Go to Main Menu

        this.startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login Failed, Try Again", Toast.LENGTH_LONG).show();

        btnLgn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String password = etPword.getText().toString();
        if (!password.equals(User.passWord)) {
            etPword.setError("Wrong Password");
            valid = false;
        } else {
            etPword.setError(null);
        }

        return valid;
    }
}
