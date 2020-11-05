package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BusDetailsActivity extends AppCompatActivity {
    private AutoCompleteTextView txtName,txtType,txtTime,txtType1,txtTime2,txtName3;
    private Button btnBook;
    FirebaseDatabase root;
    DatabaseReference reference;
    RecyclerView mRecyclerDetail;
    ArrayList<Detail> data;
    BusDetailAdapter adapter;
    AutoCompleteTextView busTime,busName,busType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Bus Details");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtType = findViewById(R.id.bus_type_detail);
        txtTime = findViewById(R.id.bus_time_detail);
        txtName = findViewById(R.id.bus_name_detail);
        btnBook = findViewById(R.id.book_proceed);



        data = new ArrayList();
        mRecyclerDetail = findViewById(R.id.recycler_detail);
        mRecyclerDetail.setHasFixedSize(true);


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bsName = txtName.getText().toString().trim();
                String bsType = txtType.getText().toString().trim();
                String bsTime = txtTime.getText().toString().trim();

                if (bsName.isEmpty()) {
                    txtName.setError("Field cannot be empty");
                }
                else if (bsType.isEmpty()){
                    txtType.setError("Field cannot be empty");
                }
                else if (bsTime.isEmpty()){
                    txtTime.setError("Field cannot be empty");
                }
                else {
                    proceed(bsName,bsType,bsTime);
                }

            }
        });

        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mlinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); // set Horizontal Orientation
        mRecyclerDetail.setLayoutManager(mlinearLayoutManager); // set LayoutManager to RecyclerView

        Detail p1 = new Detail("LUXURY", "Full AC","Charging System","Free Bites","Tshs 36,000",R.drawable.bus_land_page);
        Detail p2 = new Detail("SEMI-LUXURY", "No AC","Charging System","Free Bites","Tshs 33,000",R.drawable.bus_land_page);
        Detail p3 = new Detail("ORDINARY", "No AC","Charging System","No Free Bites","Tshs 25,000",R.drawable.bus_land_page);

        data.add(p1);
        data.add(p2);
        data.add(p3);

        adapter = new BusDetailAdapter(this,data);
        mRecyclerDetail.setAdapter(adapter);

        busType = findViewById(R.id.bus_type_detail);
        busTime= findViewById(R.id.bus_time_detail);
        busName = findViewById(R.id.bus_name_detail);

        String[] txt1Time  = {"5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM"};
        String[] txt2Type  = {"Luxury", "Semi-luxury", "Ordinary"};
        String[] txt3Name = {"DarExpress", "Shabibi", "Dar Lux", "Tilisho", "ABOOD"};

        ArrayAdapter busType_ad = new ArrayAdapter(this, R.layout.drop_down_iterms, txt2Type);
        ArrayAdapter busTime_ad = new ArrayAdapter(this, R.layout.drop_down_iterms, txt1Time);
        ArrayAdapter busName_ad = new ArrayAdapter(this, R.layout.drop_down_iterms, txt3Name);

        busName .setText(busName_ad.getItem(0).toString(), false);
        busName.setAdapter(busName_ad);

        busType .setText(busType_ad.getItem(0).toString(), false);
        busType.setAdapter(busType_ad);

        busTime .setText(busTime_ad.getItem(0).toString(), false);
        busTime.setAdapter(busTime_ad);

    }
    private void proceed(String bsName,String bsType,String bsTime){
        //upload data to database
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("BusDetails");

        DetailDb detailDb = new DetailDb(bsName,bsType,bsTime);

        reference.setValue(detailDb);


        //getting data from previous activity
        Bundle bundle = getIntent().getExtras();

        //pass date to string variable
        String homeFrom = bundle.getString("homeFromData");
        String homeTo = bundle.getString("homeToData");
        String homeDate = bundle.getString("homeDateData");


        Intent intent = new Intent(BusDetailsActivity.this, BusImageActivity.class);
        //passing data from one activity to next
        intent.putExtra("busNameData",bsName);
        intent.putExtra("busTypeData",bsType);
        intent.putExtra("busTimeData",bsTime);

        //data from home activity
        intent.putExtra("fromData",homeFrom);
        intent.putExtra("toData",homeTo);
        intent.putExtra("dateData",homeDate);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.appmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_logout){

            Intent intent = new Intent(BusDetailsActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(BusDetailsActivity.this,NavigationDrawerActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}
