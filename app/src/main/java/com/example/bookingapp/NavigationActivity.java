package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity {
    private TextInputLayout selectDate;
    AutoCompleteTextView autoCompleteTextView, autoCompleteTextViewTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        selectDate = findViewById(R.id.select_date);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("SELECT A DATE");
        MaterialDatePicker materialDatePicker = builder.build();

        selectDate.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "datepicker");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                //Toast.makeText(NavigationActivity.this, selection.toString(), Toast.LENGTH_SHORT).show();
                //convert time selected timestamp to normal date then show in selectDate
                    Calendar cal = Calendar.getInstance((Locale) selection);
                    String date = DateFormat.format("dd-MM-yyyy", cal).toString();
                    selectDate.getEditText().setText(date);

            }
        });

        autoCompleteTextView = findViewById(R.id.autoComplete);
        autoCompleteTextViewTo = findViewById(R.id.autoCompleteTo);


        String[] optionfrom = {"Kilimanjaro", "Dodoma", "Arusha", "Manyara", "Kigoma"};
        String[] optionto = {"Rukwa", "Lindi", "Dar es Salaam", "Mbeya", "Singida"};

        ArrayAdapter adapterfrom = new ArrayAdapter(this, R.layout.drop_down_iterms, optionfrom);
        ArrayAdapter adapterto = new ArrayAdapter(this, R.layout.drop_down_iterms, optionto);

        autoCompleteTextView.setText(adapterfrom.getItem(0).toString(), false);
        autoCompleteTextView.setAdapter(adapterfrom);

        autoCompleteTextViewTo.setText(adapterto.getItem(0).toString(), false);
        autoCompleteTextViewTo.setAdapter(adapterto);


    }
}