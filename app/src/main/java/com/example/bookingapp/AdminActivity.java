package com.example.bookingapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AdminActivity extends AppCompatActivity {
    private static final int PIC_IMAGE_REQUEST = 1;
    private Button btnChoose,btnUpload,btnShow;
    private AutoCompleteTextView txtFrom,txtTo,txtName,txtType,txtTime;
    private ImageView imgView;
    private ProgressBar progressBar;
    private Uri imgUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask uploadTask;
    AutoCompleteTextView busTo,busFrom,busName,busType,busTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Upload Bus Details");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnChoose = findViewById(R.id.bus_image);
        btnShow = findViewById(R.id.view_bus);
        btnUpload = findViewById(R.id.add_bus);
        txtTime = findViewById(R.id.bus_time);
        txtFrom = findViewById(R.id.bus_from);
        txtTo = findViewById(R.id.bus_to);
        txtName = findViewById(R.id.bus_name);
        txtType = findViewById(R.id.bus_type);
        imgView = findViewById(R.id.img_admn);
        progressBar = findViewById(R.id.progress_bar);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ImageActivity.class));
            }
        });


        busFrom = findViewById(R.id.bus_from);
        busTo = findViewById(R.id.bus_to);
        busName = findViewById(R.id.bus_name);
        busType = findViewById(R.id.bus_type);
        busTime = findViewById(R.id.bus_time);

        String[] bus_from = {"Kilimanjaro", "Dodoma", "Arusha", "Manyara", "Kigoma"};
        String[] bus_to = {"Rukwa", "Lindi", "Dar es Salaam", "Mbeya", "Singida"};
        String[] bus_type = {"Luxury", "Semi-luxury", "Ordinary"};
        String[] bus_name = {"DarExpress", "Shabibi", "Dar Lux", "Tilisho", "ABOOD"};
        String[] bus_Time = {"5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM", "9:00 AM"};

        ArrayAdapter adapter_from = new ArrayAdapter(this, R.layout.drop_down_iterms, bus_from);
        ArrayAdapter adapter_to = new ArrayAdapter(this, R.layout.drop_down_iterms, bus_to);
        ArrayAdapter adapter_type = new ArrayAdapter(this, R.layout.drop_down_iterms, bus_type);
        ArrayAdapter adapter_name = new ArrayAdapter(this, R.layout.drop_down_iterms, bus_name);
        ArrayAdapter adapter_time = new ArrayAdapter(this, R.layout.drop_down_iterms, bus_Time);

        busFrom .setText(adapter_from.getItem(0).toString(), false);
        busFrom.setAdapter(adapter_from);

        busTo .setText(adapter_to.getItem(0).toString(), false);
        busTo.setAdapter(adapter_to);

        busName .setText(adapter_name.getItem(0).toString(), false);
        busName.setAdapter(adapter_name);

        busType .setText(adapter_type.getItem(0).toString(), false);
        busType.setAdapter(adapter_type);

        busTime .setText(adapter_time.getItem(0).toString(), false);
        busTime.setAdapter(adapter_time);

    }

    //open file chooser
    public  void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PIC_IMAGE_REQUEST );
    }

    //set a chosen image to the image view
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PIC_IMAGE_REQUEST && resultCode == RESULT_OK && data != null){
            imgUri = data.getData();
            Glide.with(this).load(imgUri).into(imgView);
        }
    }

    //getting file extention
    private  String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //upload the image and texts
    private void  uploadFile(){
        if (imgUri != null){
            StorageReference fileReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtention(imgUri));
            uploadTask = fileReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Thread delay = new Thread(){
                        @Override
                        public  void  run(){
                            try {
                                sleep(500);
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                        }
                    };
                    delay.start();
                    Toast.makeText(AdminActivity.this, "Uploaded Successful", Toast.LENGTH_SHORT).show();
                    Upload upload = new Upload(txtName.getText().toString(),txtType.getText().toString(),txtFrom.getText().toString(),txtTo.getText().toString(),txtTime.getText().toString(),taskSnapshot.getStorage().getDownloadUrl().toString(),String.valueOf(System.currentTimeMillis()));
                    String uploadID = databaseReference.push().getKey();
                    databaseReference.child(System.currentTimeMillis()+"").setValue(upload);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });

        }else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
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

            Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(AdminActivity.this,AdminHomeActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}