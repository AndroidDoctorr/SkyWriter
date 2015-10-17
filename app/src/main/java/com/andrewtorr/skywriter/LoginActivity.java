package com.andrewtorr.skywriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Andrew on 7/23/2015.
 *
 */
public class LoginActivity extends Activity {
    Button loginButton;
    Button signupButton;
    EditText usernameTxt;
    EditText passwordTxt;
    TextView passwordCheckLbl;
    EditText passwordCheckTxt;
    String username;
    String password;
    String password2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameTxt = (EditText) findViewById(R.id.txtusername);
        passwordTxt = (EditText) findViewById(R.id.txtpassword);
        passwordCheckTxt = (EditText) findViewById(R.id.password_check);
        passwordCheckLbl = (TextView) findViewById(R.id.lblpassword2);
        loginButton = (Button) findViewById(R.id.login);
        signupButton = (Button) findViewById(R.id.signup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exist and authenticated, send user to Welcome.class
                            Intent intent = new Intent(LoginActivity.this, ScriptListActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),
                                    "Successfully Logged in!",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Invalid username or password.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTxt.getText().toString();
                password = passwordTxt.getText().toString();
                password2 = passwordCheckTxt.getText().toString();

                passwordCheckTxt.setVisibility(View.VISIBLE);
                passwordCheckLbl.setVisibility(View.VISIBLE);

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please complete the sign up form",
                            Toast.LENGTH_LONG).show();

                } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                if (password.equals(password2)) {
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Signed up!",
                                            Toast.LENGTH_LONG).show();
                                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                                        @Override
                                        public void done(ParseUser user, ParseException e) {
                                            // If user exist and authenticated, send user to Welcome.class
                                            Intent intent = new Intent(LoginActivity.this, ScriptListActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Confirm your password",
                                            Toast.LENGTH_LONG).show();
                                }

                            } else {
                                Log.d("TEST", e.toString());
                                Toast.makeText(getApplicationContext(),
                                        "Sign up Error", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });
    }
}
