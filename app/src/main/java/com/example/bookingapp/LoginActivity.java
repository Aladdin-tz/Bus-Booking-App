package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btnSignup, btnFgt, btnLogin;
    ImageView imgLog;
    TextView txtBg, txtSm;
    TextInputEditText mEdtMail, mEdtPass;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        //if(mAuth.getCurrentUser() != null){
        //   finish();
        //  startActivity(new Intent(getApplicationContext(),NavigationDrawerActivity.class));
        //}

        btnFgt = findViewById(R.id.btn_forget_login);
        btnLogin = findViewById(R.id.btn_login);
        btnSignup = findViewById(R.id.btn_signup_login);
        imgLog = findViewById(R.id.img_log);
        txtBg = findViewById(R.id.txt_log_bg);
        txtSm = findViewById(R.id.txt_log_sm);
        mEdtMail = findViewById(R.id.edt_mail);
        mEdtPass = findViewById(R.id.edt_pass);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Logging");
        dialog.setMessage("Please wait...");

        btnFgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);

                Pair[] pairs = new Pair[8];
                pairs[0] = new Pair<View, String>(imgLog, "img_trs");
                pairs[1] = new Pair<View, String>(txtBg, "txt_trs");
                pairs[2] = new Pair<View, String>(txtSm, "txt_sm_trs");
                pairs[3] = new Pair<View, String>(mEdtMail, "txt_email_trs");
                pairs[4] = new Pair<View, String>(mEdtPass, "txt_psw_trs");
                pairs[5] = new Pair<View, String>(btnFgt, "img_fgt_trs");
                pairs[6] = new Pair<View, String>(btnLogin, "img_login_trs");
                pairs[7] = new Pair<View, String>(btnSignup, "txt_signup_trs");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailReg = mEdtMail.getText().toString().trim();
                String pswReg = mEdtPass.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (emailReg.isEmpty()) {
                    mEdtMail.setError("Enter email");
                } else if (!emailReg.matches(emailPattern)) {
                    mEdtMail.setError("Invalid email address");
                } else if (pswReg.isEmpty()) {
                    mEdtPass.setError("Enter password");
                } else if (pswReg.length() < 6) {
                    mEdtPass.setError("Minimum Password length should be 6 Characters");
                } else {
                    login(emailReg, pswReg);
                }
            }
        });
    }

    private void login(String emailReg, String pswReg) {

        dialog.show();
        //upload data to database
        mAuth.signInWithEmailAndPassword(emailReg, pswReg).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //dismiss dialog
                dialog.dismiss();

                if (task.isSuccessful()) {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    databaseReference.child("users").child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User user = snapshot.getValue(User.class);
                            // Log.e("time", "onDataChange" + user.getEmailRegister());


                            if (user.getRole().equals("admin")) {

                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                clear();
                                Toast.makeText(LoginActivity.this, "Logged Successfully", Toast.LENGTH_LONG).show();
                            } else {

                                Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                                startActivity(intent);
                                clear();
                                Toast.makeText(LoginActivity.this, "Logged Successfully", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "onCancelled: " + error.getMessage());
                        }
                    });
                } else {
                    dialog.dismiss();
                    message("FAILED!!", "Please enter correct Email and Password");
                    clear();
                }
            }
        });

    }

    public void message(String tittle, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    public void clear() {
        mEdtMail.setText("");
        mEdtPass.setText("");
    }
}
