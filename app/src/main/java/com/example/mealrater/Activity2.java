package com.example.mealrater;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Activity2 extends AppCompatActivity {
    DatabaseHelper myDb;
    RatingBar editMarks;
    Button savebtn;
    String restaurant;
    String dish;
    Button viewall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_dish);
        myDb = new DatabaseHelper(this);
        editMarks = findViewById(R.id.editText_Marks);
        viewall = findViewById(R.id.viewdb);

        //btnAddData = findViewById(R.id.savebtn);
        savebtn = findViewById(R.id.savebtn);
        Intent intent = getIntent();
        //String restaurant1 = intent.getStringArrayExtra("Restaurant");
        restaurant = intent.getStringExtra ( "Restaurant" );
        dish = intent.getStringExtra ( "Dish" );

        //savebtn = findViewById(R.id.button_show);
        AddData();
        viewAll();
//        UpdateData();
//        DeleteData();
        //output.setText(getString(R.string.welcome_message));
        //output.setText(getString(R.string.welcome_message));
        //linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        // savebtn= findViewById(R.id.button_show);
        // savebtn.setOnClickListener(v -> {
//        savebtn.setOnClickListener(v ->
//                startActivity(new Intent(Activity2.this,
//                        MainActivity.class))
//        );

    }
//
//    public void DeleteData() {
//        btnDelete.setOnClickListener(
//                v -> {
//                    Integer deletedRows = myDb.deleteData(editTextId.getText().toString());
//                    if(deletedRows > 0)
//                        Toast.makeText(Activity2.this,"Data Deleted",Toast.LENGTH_LONG).show();
//                    else
//                        Toast.makeText(Activity2.this,"Data not Deleted",Toast.LENGTH_LONG).show();
//                }
//        );
//    }
//    public void UpdateData() {
//        btnviewUpdate.setOnClickListener(
//                v -> {
//                    boolean isUpdate = myDb.updateData(
//                            editTextId.getText().toString(), editName.getText().toString(),
//                            editSurname.getText().toString(), editMarks.getText().toString());
//                    if(isUpdate)
//                        Toast.makeText(Activity2.this,"Data Update",Toast.LENGTH_LONG).show();
//                    else
//                        Toast.makeText(Activity2.this,"Data not Updated",Toast.LENGTH_LONG).show();
//                }
//        );
//    }
    public  void AddData() {
        Cursor res = myDb.getAllData();
        Intent myIntent = new Intent(Activity2.this, MainActivity.class);
        savebtn.setOnClickListener(
                v -> {
                    boolean isInserted = myDb.insertData(
                            null,
                            restaurant,
                            dish,
                            editMarks.getRating());
                    if(isInserted){
                        Toast.makeText(Activity2.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        //output.setText(res.getColumnName(4));
                    }
                    else
                        Toast.makeText(Activity2.this,"Data not Inserted",Toast.LENGTH_LONG).show();
//                    myIntent.putExtra("Restaurant", restaurant.getText().toString()); //Optional parameters
//                    myIntent.putExtra("Dish", dish.getText().toString()); //Optional parameters
                    Activity2.this.startActivity(myIntent);
                }
        );
    }
//
    public void viewAll() {
        viewall.setOnClickListener(
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
                        buffer.append("Restaurant :").append(res.getString(1)).append("\n");
                        buffer.append("Dish :").append(res.getString(2)).append("\n");
                        buffer.append("Marks :").append(res.getString(3)).append("\n\n");
                        String string3 = res.getString(3);


                    }

                    // Show all data
                    showMessage("Data",buffer.toString());
                }
        );
    }
//
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
//
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
//
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