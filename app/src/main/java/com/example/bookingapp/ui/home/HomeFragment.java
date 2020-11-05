package com.example.bookingapp.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingapp.BusDetailsActivity;
import com.example.bookingapp.BusImageActivity;
import com.example.bookingapp.Home;
import com.example.bookingapp.LoginActivity;
import com.example.bookingapp.R;
import com.example.bookingapp.RegistrationActivity;
import com.example.bookingapp.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private TextInputLayout selectDate;
    private Button searchBus;
    FirebaseDatabase rootNode;
    DatabaseReference dbReference;
    private ProgressDialog dialog;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextViewTo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        searchBus = root.findViewById(R.id.search_bus);
        selectDate = root.findViewById(R.id.select_date);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        MaterialDatePicker materialDatePicker = builder.build();

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Saving");
        dialog.setMessage("Please wait...");

        searchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFrom = autoCompleteTextView.getText().toString().trim();
                String userTo = autoCompleteTextViewTo.getText().toString().trim();
                String userDate = selectDate.getEditText().toString().trim();

                if (userFrom.isEmpty()) {
                    autoCompleteTextView.setError("Field cannot be empty");
                }
                else if (userTo.isEmpty()){
                    autoCompleteTextViewTo.setError("Field cannot be empty");
                }
                else if (userDate.isEmpty()){
                    selectDate.setError("Field cannot be empty");
                }
                else {
                    search(userFrom,userTo,userDate);
                }

            }
        });

        selectDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getFragmentManager(), "datepicker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                //Toast.makeText(NavigationActivity.this, selection.toString(), Toast.LENGTH_SHORT).show();
                //convert time selected timestamp to normal date then show in selectDate
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                String date = DateFormat.format(selection.toString(), cal).toString();
                SimpleDateFormat df =  new SimpleDateFormat("dd-MM-yy");
                Log.d("time", "onPositiveButtonClick: "+df.format(selection));
                selectDate.getEditText().setText(df.format(selection));
            }
        });

        autoCompleteTextView = root.findViewById(R.id.autoComplete);
        autoCompleteTextViewTo = root.findViewById(R.id.autoCompleteTo);


        String[] optionfrom = {"Kilimanjaro", "Dodoma", "Arusha", "Manyara", "Kigoma"};
        String[] optionto = {"Rukwa", "Lindi", "Dar es Salaam", "Mbeya", "Singida"};

        ArrayAdapter adapterfrom = new ArrayAdapter(getActivity(), R.layout.drop_down_iterms, optionfrom);
        ArrayAdapter adapterto = new ArrayAdapter(getActivity(), R.layout.drop_down_iterms, optionto);

        autoCompleteTextView.setText(adapterfrom.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(adapterfrom);

        autoCompleteTextViewTo.setText(adapterto.getItem(0).toString(), false);
        autoCompleteTextViewTo.setAdapter(adapterto);


        return root;
    }

    private void search(String userFrom,String userTo,String userDate){
        //upload data to database
        rootNode = FirebaseDatabase.getInstance();
        dbReference = rootNode.getReference("location");

        Home home = new Home(userFrom,userTo,userDate);

        dbReference.setValue(home);
        String passHomeFrom = autoCompleteTextView.getText().toString().trim();
        String passHomeTo = autoCompleteTextViewTo.getText().toString().trim();
        String passHomeDate = selectDate.getEditText().toString().trim();

        Intent intent = new Intent(getActivity(), BusDetailsActivity.class);
        //passing data from one activity to next
        intent.putExtra("homeFromData",passHomeFrom);
        intent.putExtra("homeToData",passHomeTo);
        intent.putExtra("homeDateData",passHomeDate);

        startActivity(intent);

    }
}