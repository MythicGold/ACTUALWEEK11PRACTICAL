package com.practical.actualweek11practical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUserActivity extends AppCompatActivity {

    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        db = new DbHandler(this, null, null, 1);
    }

    public void onCreateUser(View v){
        EditText editTextUser = findViewById(R.id.edUNC);
        EditText editTextPass = findViewById(R.id.edPWC);

        String username = editTextUser.getText().toString();
        String password = editTextPass.getText().toString();

        Pattern userP = Pattern.compile("^[A-Za-z0-9]{6,12}$");
        Pattern passP = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*");

        Matcher userMatcher = userP.matcher(username);
        Matcher passMatcher = passP.matcher(password);

        if (userMatcher.matches() && passMatcher.matches()){
            //Add Account to Database
            Account a = new Account(username, password);
            db.addAccount(a);

            //Add Account to Shared Preferences
            SharedPreferences.Editor editor = getSharedPreferences("MY_GLOBAL_PREFS", MODE_PRIVATE).edit();
            editor.putString("Username", username);
            editor.putString("Password", password);
            editor.apply();

            //Create Toast
            Toast bread = Toast.makeText(CreateUserActivity.this, "New User Created Successfully", Toast.LENGTH_LONG);
            bread.show();

            //Redirect to main page
            Intent in = new Intent(CreateUserActivity.this, MainActivity.class);
            startActivity(in);
        }
        else{
            Toast bread = Toast.makeText(CreateUserActivity.this, "Invalid User Creation. Please try again!", Toast.LENGTH_LONG);
            bread.show();
        }
    }

    public void onBack(View v){
        Intent in = new Intent(CreateUserActivity.this, MainActivity.class);
        startActivity(in);
    }
    /*
    EditText txtUsername = findViewById(R.id.edUsername);
    String inputUsername = txtUsername.getText().toString();

    EditText txtPassword = findViewById(R.id.edPassword);
    String inputPassword = txtPassword.getText().toString();

    // Requirements: Users must input
    Pattern patternUN = Pattern.compile("^[A-Za-z0-9]{6,12}$");
    Matcher matcherUN = patternUN.matcher(inputUsername);
    Pattern patternPW = Pattern.compile("^(?=.*[A-Z])(?=.*[0-9])(?=.*\\W).*");
    Matcher matcherPW = patternPW.matcher(inputPassword);

    TextView result = findViewById(R.id.textView5);
        result.setText("" + matcher.matches());

    Account a = new Account(inputPassword, "password");
        db.addAccount(a);
    */

}
