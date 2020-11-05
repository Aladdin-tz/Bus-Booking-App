package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusImageActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private BusImageAdapter mimageAdapter;
    private ProgressDialog mprogressDialog;
    private FirebaseStorage mfirebaseStorage;
    private DatabaseReference mdatabaseReference;
    private ValueEventListener mvalueEventListener;
    private List<Bus> muploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_image);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Available Buses");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mrecyclerView  = findViewById(R.id.recycler_view_bus);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploads = new ArrayList<>();
        mimageAdapter = new BusImageAdapter(this, muploads);

        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setTitle("Fetching Bus");
        mprogressDialog.setMessage("Please wait...");

        mfirebaseStorage = FirebaseStorage.getInstance();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference();
        mprogressDialog.show();

        //getting data from previous activity
        String busName = getIntent().getStringExtra("busNameData");
        String busType = getIntent().getStringExtra("busTypeData");
        String busTime = getIntent().getStringExtra("busTimeData");

        String fromBus = getIntent().getStringExtra("fromData");
        String toBus = getIntent().getStringExtra("toData");
        String dateBus = getIntent().getStringExtra("dateData");

        FirebaseDatabase.getInstance().getReference("uploads")
                .orderByChild("busFrom")
                .equalTo(fromBus)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        muploads.clear();
                        mprogressDialog.dismiss();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Bus bus = snapshot.getValue(Bus.class);
                                muploads.add(bus);
                            }
                            mimageAdapter.notifyDataSetChanged();
                        }
                        FirebaseDatabase.getInstance().getReference()
                                .child("uploads")
                                .orderByChild("busTo")
                                .equalTo(toBus)
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        muploads.clear();
                                        mprogressDialog.dismiss();
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                Bus bus = snapshot.getValue(Bus.class);
                                                if (bus.getBusFrom().equals(fromBus) && bus.getBusTo().equals(toBus) && bus.getBusType().equals(busType) && bus.getBusName().equals(busName) && bus.getBusTime().equals(busTime)){

                                                    muploads.add(bus);

                                                }
                                            }
                                            mimageAdapter.notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        mprogressDialog.dismiss();
                                        Toast.makeText(BusImageActivity.this,"Firebase Database Error",Toast.LENGTH_LONG).show();
                                    }
                                });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mprogressDialog.dismiss();
                        Toast.makeText(BusImageActivity.this,"Firebase Database Error",Toast.LENGTH_LONG).show();
                    }
                });

        mrecyclerView.setAdapter(mimageAdapter);
    }
    public  void message(String tittle,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(BusImageActivity.this,NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
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

            Intent intent = new Intent(BusImageActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(BusImageActivity.this,BusDetailsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}