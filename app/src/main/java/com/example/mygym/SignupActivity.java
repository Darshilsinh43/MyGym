package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
EditText name,email,ph_number,password,conf_passwd,dob;
Button signup,login;
RadioGroup gender;
String sGender;
SQLiteDatabase db;


    String emailvalid = "[a-z0-9.-_]+@[a-z]+\\.[a-z]{2,3}";
    String ph_valid = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
     db = openOrCreateDatabase("MyGym",MODE_PRIVATE,null);
      String table_Query = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20),DOB VARCHAR(8),GENDER VARCHAR(6))";
      db.execSQL(table_Query);



        Calendar calendar = Calendar.getInstance();
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        ph_number = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_passwd);
        conf_passwd = findViewById(R.id.signup_confpasswd);
        signup = findViewById(R.id.signup_btn);
        login = findViewById(R.id.login_btn);
        dob = findViewById(R.id.signup_date);
        gender = findViewById(R.id.gender);
   signup.setOnClickListener(new View.OnClickListener() {
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
           } else if (!password.getText().toString().equals(conf_passwd.getText().toString())) {
               conf_passwd.setError("Password Does not Match");
           } else if (!ph_number.getText().toString().trim().matches(ph_valid)) {
               ph_number.setError("Enter Valid Phone Number");
           } else if (gender.getCheckedRadioButtonId() == -1) {
                new common_behave(SignupActivity.this,"Select Gender");
           } else {
               String insert_Query ="INSERT INTO USERS VALUES(null,'"+name.getText().toString()+"','"+email.getText().toString()+"','"+ph_number.getText().toString()+"','"+password.getText().toString()+"','"+dob.getText().toString()+"','"+sGender+"') ";
               db.execSQL(insert_Query);
               new common_behave(SignupActivity.this, "Signup Sucessfully");



               onBackPressed();

           }


       }
   });
        DatePickerDialog.OnDateSetListener dateclick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                dob.setText(sdf.format(calendar.getTime()));

            }
        };


   dob.setOnTouchListener(new View.OnTouchListener() {
       @Override
       public boolean onTouch(View view, MotionEvent motionEvent) {
           DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
           datePickerDialog.show();
           datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
           return true;
       }
   });
gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        RadioButton radioButton = findViewById(i);
        sGender = radioButton.getText().toString();
    }
});
   login.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           onBackPressed();
       }
   });


    }
}