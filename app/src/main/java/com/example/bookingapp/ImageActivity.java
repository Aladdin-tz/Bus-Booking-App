package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class ImageActivity extends AppCompatActivity {
    private RecyclerView mrecyclerView;
    private ImageAdapter mimageAdapter;
    private ProgressDialog mprogressDialog;
    private FirebaseStorage mfirebaseStorage;
    private DatabaseReference mdatabaseReference;
    private ValueEventListener mvalueEventListener;
    private List<Upload> muploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mrecyclerView  = findViewById(R.id.recycler_view);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        muploads = new ArrayList<>();
        mimageAdapter = new ImageAdapter(this, muploads);

        mprogressDialog = new ProgressDialog(this);
        mprogressDialog.setTitle("Loading");
        mprogressDialog.setMessage("Please wait...");

        mfirebaseStorage = FirebaseStorage.getInstance();
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");
        mprogressDialog.show();
        mvalueEventListener = mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                muploads.clear();
                for (DataSnapshot postSnapshot :dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    muploads.add(upload);
                    Collections.reverse(muploads);

                }
                mimageAdapter.notifyDataSetChanged();
                mprogressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ImageActivity.this, "Database is locked", Toast.LENGTH_SHORT).show();
            }
        });
        mrecyclerView.setAdapter(mimageAdapter);
    }
}