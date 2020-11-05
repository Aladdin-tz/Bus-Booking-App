package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PassengerDetailsActivity extends AppCompatActivity {
    private TextInputEditText txtFullname,txtEmail,txtPhone;
    private Button btnConfirm;
    FirebaseDatabase root,rootFn;
    DatabaseReference reference,referenceFn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Passenger Details");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtFullname = findViewById(R.id.txt_fullname);
        txtEmail = findViewById(R.id.txt_email);
        txtPhone = findViewById(R.id.txt_phone);
        btnConfirm= findViewById(R.id.btn_submit);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String psName = txtFullname.getText().toString().trim();
                String psEmail = txtEmail.getText().toString().trim();
                String psPhone = txtPhone.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (psName.isEmpty()) {
                    txtFullname.setError("Field cannot be empty");
                }
                else if (psEmail.isEmpty()){
                    txtEmail.setError("Field cannot be empty");
                }
                else if (!psEmail.matches(emailPattern)) {
                    txtEmail.setError("Invalid email address");
                }
                else if (psPhone.isEmpty()){
                    txtPhone.setError("Field cannot be empty");
                }
                else {
                    confirm(psName,psEmail,psPhone);
                }
            }
        });
    }
    private void confirm(String psName,String psEmail,String psPhone){

        String nameFn = getIntent().getStringExtra("busNameFn");
        String typeFn = getIntent().getStringExtra("busTypeFn");
        String fromFn = getIntent().getStringExtra("busFromFn");
        String timeFn = getIntent().getStringExtra("busTimeFn");
        String toFn = getIntent().getStringExtra("busToFn");

        // Upload to database
        rootFn = FirebaseDatabase.getInstance();
        referenceFn = rootFn.getReference("FinalDetails");

        Final finalFn = new Final(nameFn,typeFn,fromFn,timeFn,psName,psEmail,psPhone,toFn,String.valueOf(System.currentTimeMillis()));
        //PassengerDb passengerDb = new PassengerDb(psName,psEmail,psPhone);

        referenceFn.child(System.currentTimeMillis()+"").setValue(finalFn);


//        referenceFn.child(System.currentTimeMillis()+"1").setValue(nameFn);
//        referenceFn.child(System.currentTimeMillis()+"2").setValue(typeFn);
//        referenceFn.child(System.currentTimeMillis()+"3").setValue(timeFn);
//        referenceFn.child(System.currentTimeMillis()+"4").setValue(fromFn);


//        //upload data to database
//        root = FirebaseDatabase.getInstance();
//        reference = root.getReference("PassengerDetails");
//
//        PassengerDb passengerDb = new PassengerDb(psName,psEmail,psPhone);
//
//        reference.child(System.currentTimeMillis()+"").setValue(passengerDb);
        //Toast.makeText(this, "Bus Booked Successfully", Toast.LENGTH_LONG).show();
        message("BUS BOOKED","Bus Booked Successfully");


    }

    public  void message(String tittle,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(PassengerDetailsActivity.this, NavigationDrawerActivity.class);
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

            Intent intent = new Intent(PassengerDetailsActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(PassengerDetailsActivity.this,BusDetailsActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

}