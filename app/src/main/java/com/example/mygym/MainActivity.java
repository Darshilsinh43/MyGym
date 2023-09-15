package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button login,signup;
String emailvalid = "[a-z0-9.-_]+@[a-z]+\\.[a-z]{2,3}";
EditText email,password;
SQLiteDatabase db;
ImageView hide,show;
CheckBox rememberMe;
SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      sp = getSharedPreferences(Constantsp.PREF,MODE_PRIVATE);
        db = openOrCreateDatabase("MyGym",MODE_PRIVATE,null);
        String table_Query = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20),DOB VARCHAR(8),GENDER VARCHAR(6))";
        db.execSQL(table_Query);
        login = findViewById(R.id.login);
    email = findViewById(R.id.email);
    signup = findViewById(R.id.signup);
    password = findViewById(R.id.passwd);
    rememberMe = findViewById(R.id.check_box);
    hide = findViewById(R.id.hide_pass);
    show = findViewById(R.id.show_pass);
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (email.getText().toString().trim().equals("")){
                email.setError("Email Required");
            } else if (!email.getText().toString().trim().matches(emailvalid)) {
                email.setError("Enter Vaid Email Id");
            } else if (password.getText().toString().trim().equals("")) {
                password.setError("Password Required");
            } else if (password.getText().toString().trim().length()<6) {
                password.setError("Password Must Have 6 Characters");
            }
            else {
                String select_Query="SELECT * FROM USERS WHERE EMAIL = '"+email.getText().toString()+"' AND PASSWORD = '"+password.getText().toString()+"'";
                Cursor cursor = db.rawQuery(select_Query,null);
                if (cursor.getCount()>0){
                    while (cursor.moveToNext()){
                        String UserId = cursor.getString(0);
                        String sName = cursor.getString(1);
                        String sEmail = cursor.getString(2);
                        String sContact = cursor.getString(3);
                        String sPassword = cursor.getString(4);
                        String sDob = cursor.getString(5);
                        String s_gender = cursor.getString(6);
                        sp.edit().putString(Constantsp.Id,UserId).commit();
                        sp.edit().putString(Constantsp.Name,sName).commit();
                        sp.edit().putString(Constantsp.Email,sEmail).commit();
                        sp.edit().putString(Constantsp.Contact,sContact).commit();
                        sp.edit().putString(Constantsp.Password,sPassword).commit();
                        sp.edit().putString(Constantsp.Dob,sDob).commit();
                        sp.edit().putString(Constantsp.Gender,s_gender).commit();
                        if (rememberMe.isChecked()){
                            sp.edit().putString(Constantsp.Remember,"Yes").commit();
                        }
                        else{
                            sp.edit().putString(Constantsp.Remember,"").commit();
                        }
                    }
                    new common_behave(MainActivity.this, "LogIn Successfully");
                    new common_behave(MainActivity.this,Dashboard_activity.class);
                }
                else {
                    new common_behave(MainActivity.this,"Email Id or Password is not Match");
                }

            }}
    });
   hide.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
       hide.setVisibility(view.GONE);
       show.setVisibility(view.VISIBLE);
       password.setTransformationMethod(new PasswordTransformationMethod());
       }
   });
   show.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
        hide.setVisibility(view.VISIBLE);
        show.setVisibility(view.GONE);
        password.setTransformationMethod(null);
       }
   });

    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           new common_behave(MainActivity.this,SignupActivity.class);
        }
    });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    finishAffinity();
    }
}