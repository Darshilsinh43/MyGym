package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Account_activity extends Fragment {
    EditText name,email,ph_number,dob;
    Button update,logout,edit;

    RadioGroup gender;
    RadioButton male,female;
    String sGender;
    SharedPreferences sp;
    SQLiteDatabase db;
    String emailvalid = "[a-z0-9.-_]+@[a-z]+\\.[a-z]{2,3}";
    String ph_valid = "[0-9]{10}";
    public Account_activity() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_account, container, false);

        sp = getActivity().getSharedPreferences(Constantsp.PREF, Context.MODE_PRIVATE);

        db = getActivity().openOrCreateDatabase("MyGym",Context.MODE_PRIVATE,null);
        String table_Query = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT INT(10),PASSWORD VARCHAR(20),DOB VARCHAR(8),GENDER VARCHAR(6))";
        db.execSQL(table_Query);



        Calendar calendar = Calendar.getInstance();
        name = view.findViewById(R.id.update_name);
        email = view.findViewById(R.id.update_email);
        ph_number = view.findViewById(R.id.update_contact);
        update = view.findViewById(R.id.update_btn);
        edit = view.findViewById(R.id.edit_btn);
        logout = view.findViewById(R.id.logout_btn);
        dob = view.findViewById(R.id.update_date);
        gender = view.findViewById(R.id.gender);
        male = view.findViewById(R.id.male);
        female = view.findViewById(R.id.female);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().equals("")){
                    email.setError("Email Required");
                } else if (!email.getText().toString().trim().matches(emailvalid)) {
                    email.setError("Enter Vaid Email Id");
                } 
                else if (!ph_number.getText().toString().trim().matches(ph_valid)) {
                    ph_number.setError("Enter Valid Phone Number");
                } else if (gender.getCheckedRadioButtonId() == -1) {
                    new common_behave(getActivity(),"Select Gender");
                } else {
                    String update_Query ="SELECT * FROM USERS WHERE USERID = '" + sp.getString(Constantsp.Id,"") + "'";
                    Cursor cursor = db.rawQuery(update_Query,null);
                    if (cursor.getCount()>0){
                        String update_data = "UPDATE USERS SET NAME='"+name.getText().toString()+"',EMAIL='"+email.getText().toString()+"',CONTACT='"+ph_number.getText().toString()+"',DOB='"+dob.getText().toString()+"',GENDER='"+sGender+"' WHERE USERID='"+sp.getString(Constantsp.Id,"")+"' ";
                        db.execSQL(update_data);
                        new common_behave(getActivity(),"Update Successfully");
                        sp.edit().putString(Constantsp.Name,name.getText().toString()).commit();
                        sp.edit().putString(Constantsp.Email,email.getText().toString()).commit();
                        sp.edit().putString(Constantsp.Contact,ph_number.getText().toString()).commit();
                        sp.edit().putString(Constantsp.Dob,dob.getText().toString()).commit();
                        sp.edit().putString(Constantsp.Gender,sGender).commit();
                        setData(false);
                    }
                    else {
                        new common_behave(getActivity(), "Invalid User");
                    }








                }


            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setData(true);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateclick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                return true;
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = view.findViewById(i);
                sGender = radioButton.getText().toString();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              sp.edit().clear().commit();
                new common_behave(getActivity(),MainActivity.class);
            }
        });
       setData(false);
       return view;

    }

    private void setData(boolean isEnable) {
       name.setEnabled(isEnable);
       email.setEnabled(false);
       ph_number.setEnabled(isEnable);
       dob.setEnabled(isEnable);
       male.setEnabled(isEnable);
       female.setEnabled(isEnable);

        name.setText(sp.getString(Constantsp.Name,""));
        email.setText(sp.getString(Constantsp.Email,""));
        ph_number.setText(sp.getString(Constantsp.Contact,""));
        dob.setText(sp.getString(Constantsp.Dob,""));
        sGender = sp.getString(Constantsp.Gender,"");
        if(sGender.equalsIgnoreCase("Male")){
           male.setChecked(true);
        } else if (sGender.equalsIgnoreCase("Female")) {
            female.setChecked(true);
        }
        if(isEnable){
            edit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
        }
        else {
            edit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }
    }

  
}