package com.example.RestaurantRater;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.mealratertest.R;

import java.util.ArrayList;
import com.example.mealrater.R;import com.example.mealrater.R;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editText_restaurant_id;
    EditText editText_restaurant_name;
    EditText editText_Address;
    EditText editText_City;
    EditText editText_State;
    EditText editText_ZipCode;

    Button button_add_restaurant;
   // Button button_update;
    //Button button_viewAll;
    Button button_delete;
    Button button_show_dish;
    RecyclerView dishoutput;

//    private ArrayAdapter<String> adapter;
//    private ArrayList<String> arrayList;

    private ArrayList<String> arrayList;
    private MyAdapter adapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editText_restaurant_id = findViewById(R.id.editText_restaurant_id);
        editText_restaurant_name = findViewById(R.id.editText_restaurant_name);
        editText_Address = findViewById(R.id.editText_Address);
        editText_City = findViewById(R.id.editText_City);
        editText_State = findViewById(R.id.editText_State);
        editText_ZipCode = findViewById(R.id.editText_ZipCode);

        button_add_restaurant = findViewById(R.id.button_add_restaurant);
       // button_update = findViewById(R.id.button_update_restaurant);
        //button_viewAll = findViewById(R.id.button_viewAll_restaurant);
        //button_delete = findViewById(R.id.button_delete_restaurant);
        button_show_dish = findViewById(R.id.button_show_dish);
        dishoutput = findViewById(R.id.dishoutput);

        //arrayList = new ArrayList<String>();

        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data
        arrayList = new ArrayList<String>();
        adapter = new MyAdapter(arrayList);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        dishoutput.setLayoutManager(layoutManager);
        dishoutput.setAdapter(adapter);

        AddData();
        //viewAll();

        Intent myIntent = new Intent(MainActivity.this, MainActivity2.class);
        if (button_show_dish != null) {
            button_show_dish.setOnClickListener(v -> {
                myIntent.putExtra("Restaurant", editText_restaurant_id.getText().toString()); //Optional parameters
//              myIntent.putExtra("Dish", dish.getText().toString()); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            });
        } else {
            Log.e("MainActivity", "button_show_dish is null");
        }


        Cursor res = myDb.getAllDataDish();
        if(res.getCount() == 0) {
            // handle the case when there is no data in the database
           // value.setText("No data");
            return;
        }

        //StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            // get the value of MARKS from the database using the column index
            StringBuffer buffer = new StringBuffer();
            float marks = res.getFloat(3);
            buffer.append("ID :").append(res.getString(0)).append(",\n");
            buffer.append("").append(res.getString(1)).append(",\n");
            buffer.append("").append(res.getString(2)).append(",\n");
            buffer.append("score:").append(res.getFloat(3)).append(",\n");
            buffer.append("Restaurant_ID:").append(res.getFloat(4)).append("\n\n");
            String result = buffer.toString();
            arrayList.add(result);

        }
        adapter.notifyDataSetChanged();
//        dishoutput.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                View child = rv.findChildViewUnder(e.getX(), e.getY());
//                if (child != null && e.getAction() == MotionEvent.ACTION_UP) {
//                    int position = rv.getChildAdapterPosition(child);
//                    String item = arrayList.get(position);
//
//                    // Start your activity here using Intent
//                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                    //intent.putExtra("item", item);
//                    startActivity(intent);
//                }
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//
////            @Override
////            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
////                return false;
////            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });


    }

    public  void AddData() {
        button_add_restaurant.setOnClickListener(
                v -> {
                    boolean isInserted = myDb.insertRestaurant(
                            editText_restaurant_name.getText().toString(),
                            editText_Address.getText().toString(),
                            editText_City.getText().toString(),
                            editText_State.getText().toString(),
                            editText_ZipCode.getText().toString()
                    );
                    if(isInserted){
                        Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        //output.setText(res.getColumnName(4));
                    }
                    else
                        Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
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