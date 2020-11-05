package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    private Button btnSignin,btnReg;
    private TextInputEditText txtEmail,txtUsername,txtPhone,txtPsw;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();


        btnSignin = findViewById(R.id.btn_signin_reg);
        btnReg = findViewById(R.id.btn_reg);
        txtEmail = findViewById(R.id.edt_email_reg);
        txtPhone = findViewById(R.id.edt_phone_reg);
        txtPsw = findViewById(R.id.edt_password_reg);
        txtUsername = findViewById(R.id.edt_username_reg);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Saving");
        dialog.setMessage("Please wait...");

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userReg = txtUsername.getText().toString().trim();
                String phoneReg = txtPhone.getText().toString().trim();
                String emailReg = txtEmail.getText().toString().trim();
                String pswReg = txtPsw.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                 if (userReg.isEmpty()) {
                    txtUsername.setError("Field cannot be empty");
                }
                 else if (emailReg.isEmpty()){
                     txtEmail.setError("Enter email");
                 }
                else if (!emailReg.matches(emailPattern)) {
                    txtEmail.setError("Invalid email address");
                }
                else  if (phoneReg.isEmpty()) {
                     txtPhone.setError("Field cannot be empty");
                 }
                else if (pswReg.isEmpty()){
                    txtPsw.setError("Enter password");
                }
                else if (pswReg.length() < 6) {
                    txtPsw.setError("Minimum Password length should be 6 Characters");
                }
                else {
                    register(emailReg,pswReg,phoneReg,userReg);
                }
            }
        });
    }
    private void register(String emailReg, String pswReg,String phoneReg, String userReg){
        dialog.show();
        //upload data to database
        mAuth.createUserWithEmailAndPassword(emailReg,pswReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Add user role
                    User user = new User(emailReg,userReg,phoneReg,pswReg,"passenger");

                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                message("SUCCESS!!", "Registered Successfully, Login to start Booking");

                            }else{
                                dialog.dismiss();
                                message("FAILED!!", "Register Failed");
                                clear();
                            }
                        }
                    });

                }else{
                    dialog.dismiss();
                    message("FAILED!!", "Saving Failed");
                    clear();
                }
            }
        });
    }

    public  void message(String tittle,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
    public void clear(){
        txtUsername.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtPsw.setText("");
    }

}