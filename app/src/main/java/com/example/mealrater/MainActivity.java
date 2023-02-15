package com.example.mealrater;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    EditText restaurant;
    EditText dish;
    Button ratebtn,save;
    TextView value;

    String restaurantName;
    String dishName;

    //
    RatingBar ratingBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restaurant = (EditText) findViewById(R.id.restaurant);
        dish = (EditText) findViewById(R.id.dish);
        ratebtn = (Button) findViewById(R.id.ratebtn);
        save = (Button) findViewById(R.id.savebtn);
        value = (TextView) findViewById(R.id.valueout);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratebtn.setOnClickListener(view -> {
            restaurantName = restaurant.getText().toString();
            dishName = dish.getText().toString();
            value.setText(String.valueOf(restaurantName + dishName));
        });
    }
}