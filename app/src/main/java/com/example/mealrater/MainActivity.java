package com.example.mealrater;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText restaurant;
    EditText dish;
    Button ratebtn;
    TextView value;
    Button view1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurant = findViewById(R.id.restaurant);
        dish = findViewById(R.id.dish);
        ratebtn = findViewById(R.id.ratebtn);
        value = findViewById(R.id.valueout);
       // view1 = findViewById(R.id.viewit);

        // Initialize DatabaseHelper class instance
        myDb = new DatabaseHelper(this);
        // Set the value of the TextView to the value of MARKS in the database
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0) {
            // handle the case when there is no data in the database
            value.setText("No data");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            // get the value of MARKS from the database using the column index
            float marks = res.getFloat(3);
            buffer.append("Marks : ").append(marks).append("\n");
        }
        value.setText(buffer.toString());

        Intent myIntent = new Intent(MainActivity.this, Activity2.class);
        if (ratebtn != null) {
            ratebtn.setOnClickListener(v -> {
                myIntent.putExtra("Restaurant", restaurant.getText().toString()); //Optional parameters
                myIntent.putExtra("Dish", dish.getText().toString()); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            });
        } else {
            Log.e("MainActivity", "ratebtn is null");
        }
    }
}
