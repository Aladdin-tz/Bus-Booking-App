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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private Button backLogin,backRegister,btnReset;
    private TextInputEditText txtEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_keyboard_backspace_24);// set drawable icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        backLogin = findViewById(R.id.back_login);
        backRegister = findViewById(R.id.back_register);
        btnReset = findViewById(R.id.btn_reset);
        txtEmail = findViewById(R.id.edt_emailreset);
        firebaseAuth = FirebaseAuth.getInstance();

        backRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();

                if(email.isEmpty()){
                    Toast.makeText(ForgetPasswordActivity.this, "Please Enter Registered E-mail", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                message("SUCCESSFUL!!","Please Login To your E-MAIL to reset your Password");

                            }else {
                                Toast.makeText(ForgetPasswordActivity.this, "Error in Sending Password reset E-MAIL!", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }

            }
        });

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

            Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
            startActivity(intent);

        }else{

            Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public  void message(String tittle,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
        builder.create().show();
    }

}