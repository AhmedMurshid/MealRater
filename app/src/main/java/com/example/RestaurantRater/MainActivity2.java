package com.example.RestaurantRater;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealrater.R;

//import com.example.mealratertest.R;


public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper myDb;

    EditText editText_dish_id;
    EditText editText_dish_name;
    EditText editText_dish_type;
    Integer editText_restaurant_id;

    Button button_add_dish;
    Button button_update_dish;
    Button button_viewAll_dish;
    Button button_delete_dish;

    RatingBar ratingBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_part);
        myDb = new DatabaseHelper(this);

        editText_dish_id = findViewById(R.id.editText_dish_id);
        editText_dish_name = findViewById(R.id.editText_dish_name);
        editText_dish_type = findViewById(R.id.editText_dish_type);
        Intent intent = getIntent();
        editText_restaurant_id = Integer.valueOf(intent.getStringExtra("Restaurant"));

        button_add_dish = findViewById(R.id.button_add_dish);
        button_update_dish = findViewById(R.id.button_update_dish);
        button_viewAll_dish = findViewById(R.id.button_viewAll_dish);
        button_delete_dish = findViewById(R.id.button_delete_dish);

        ratingBar2 = findViewById(R.id.ratingBar2);

        AddData();
        viewAll();
        DeleteData();
        updateDataDish();
    }
    public void updateDataDish() {
        button_update_dish.setOnClickListener(
                v -> {
                    boolean isUpdate = myDb.updateDataDish(
                            Integer.parseInt(editText_dish_id.getText().toString()),
                            editText_dish_name.getText().toString(),
                            editText_dish_type.getText().toString(),
                            (int) ratingBar2.getRating(),
                            editText_restaurant_id);
                    if(isUpdate)
                        Toast.makeText(MainActivity2.this,"Data Update",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity2.this,"Data not Updated",Toast.LENGTH_LONG).show();
                }
        );
    }
    public void AddData() {
        button_add_dish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertDish(
                                editText_dish_name.getText().toString(),
                                editText_dish_type.getText().toString(),
                                (int) ratingBar2.getRating(),
                                editText_restaurant_id);
                        if(isInserted)
                            Toast.makeText(MainActivity2.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity2.this,"Data not Inserted",Toast.LENGTH_LONG).show();


                    }

                }
        );
    }
    public void DeleteData() {
        button_delete_dish.setOnClickListener(
                v -> {
                    Integer deletedRows = myDb.deleteDataDish(Integer.parseInt(editText_dish_id.getText().toString()));
                    if(deletedRows > 0)
                        Toast.makeText(MainActivity2.this,"Data Deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity2.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                }
        );
    }

    public void viewAll() {
        button_viewAll_dish.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllDataDish();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("DISH ID :").append(res.getString(0)).append("\n");
                            buffer.append("DISH :").append(res.getString(1)).append("\n");
                            buffer.append("DISH TYPE :").append(res.getString(2)).append("\n");
                            buffer.append("MARKS :").append(res.getFloat(3)).append("\n");
                            buffer.append("RESTAURANT ID :").append(res.getFloat(4)).append("\n\n");
                        }

                        // Show all data
                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dish_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
