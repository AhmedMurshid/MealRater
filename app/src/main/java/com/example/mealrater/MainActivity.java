package com.example.mealrater;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.internal.SafeIterableMap;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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


    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //save = getResources().getIdentifier(savebtn , "Button", getPackageName());
        restaurant = findViewById(R.id.restaurant);
        dish = findViewById(R.id.dish);
        ratebtn = findViewById(R.id.ratebtn);
        value = findViewById(R.id.valueout);
        view1 = findViewById(R.id.viewit);
        //Cursor cursor = myDb.getAllData();
//        if (cursor.moveToFirst()) {
//            @SuppressLint("Range") String value1 = cursor.getString(cursor.getColumnIndex("marks"));
//            value.setText(value1);
//        }
            ratebtn.setOnClickListener(v ->
                    startActivity(new Intent(MainActivity.this,
                            Activity2.class).putExtra("restaurant", String.valueOf(restaurant)
                    ).putExtra("dish", String.valueOf(dish))));
        }
//    public void updateTextView(String toThis) {
//        TextView textView = (TextView) findViewById(R.id.valueout);
//        textView.setText(toThis);
//    }
    public void viewAll() {
        view1.setOnClickListener(
                v -> {
                    Cursor res = myDb.getAllData();
                    if(res.getCount() == 0) {
                        // show message
                        showMessage("Error","Nothing found");
                        return;
                    }

                    StringBuilder buffer = new StringBuilder();
                    while (res.moveToNext()) {
                        buffer.append("Id :").append(res.getString(0)).append("\n");
                        buffer.append("Name :").append(res.getString(1)).append("\n");
                        buffer.append("RESTAURANT :").append(res.getString(2)).append("\n");
                        buffer.append("DISH :").append(res.getString(3)).append("\n");
                        buffer.append("Marks :").append(res.getString(4)).append("\n\n");
                    }

                    // Show all data
                    showMessage("Data",buffer.toString());
                }
        );
    }
    public void showMessage(String title,String Message){
        value.setText(Message);
    }


}
