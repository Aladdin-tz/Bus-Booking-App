package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ViewBookingActivity extends AppCompatActivity {
    RecyclerView recyclerUsers;
    ProgressDialog dialog;
    ArrayList<FetchData> passengers;
    BookingsAdapter adapter;
    private DatabaseReference mdatabaseReference;
    private ValueEventListener mvalueEventListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Passenger Bookings");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching Bookings");
        dialog.setMessage("Please wait...");

        passengers = new ArrayList();
        recyclerUsers = findViewById(R.id.recyclerView);
        recyclerUsers.setHasFixedSize(true);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookingsAdapter(this,passengers);


        mdatabaseReference = FirebaseDatabase.getInstance().getReference("FinalDetails");
        dialog.show();
        mvalueEventListener = mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                passengers.clear();
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    FetchData passenger = snapshot.getValue(FetchData.class);
                    Log.e("time", "onDataChange" + passenger);
                    //passenger.setKey(snapshot.getKey());
                    passengers.add(passenger);
                    Collections.reverse(passengers);

                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewBookingActivity.this, "Database is locked", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerUsers.setAdapter(adapter);

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

            Intent intent = new Intent(ViewBookingActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(ViewBookingActivity.this,AdminHomeActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}