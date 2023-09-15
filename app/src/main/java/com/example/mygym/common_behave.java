package com.example.mygym;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class common_behave {
    public common_behave(Context context , String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public common_behave(Context context , Class<?> classname){
        Intent intent = new Intent(context,classname);
        context.startActivity(intent);
    }
}
