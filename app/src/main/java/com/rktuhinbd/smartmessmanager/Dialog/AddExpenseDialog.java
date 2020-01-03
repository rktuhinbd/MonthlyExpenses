package com.rktuhinbd.smartmessmanager.Dialog;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rktuhinbd.smartmessmanager.Listener.AddExpenseDialogListener;
import com.rktuhinbd.smartmessmanager.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpenseDialog extends DialogFragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Spinner spinnerRentCategory;
    private EditText editTextAmount, editTextDate, editTextDescription;
    private ImageButton imageButtonClose;
    private Button buttonSave;

    private ArrayAdapter<CharSequence> rentCategoryAdapter;
    private String rentDescription, rentCategory, rentDate;
    private int rentAmount;

    private AddExpenseDialogListener dialogListener;

    public void setDialogListener(AddExpenseDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_expense, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        initiateProperties(view);
        setRentCategorySpinner(view);
        setButtonAction();

        return view;
    }

    private void initiateProperties(View view) {
        spinnerRentCategory = view.findViewById(R.id.spinner_rentCategory);
        editTextAmount = view.findViewById(R.id.editText_rentAmount);
        editTextDate = view.findViewById(R.id.editText_rentDate);
        editTextDescription = view.findViewById(R.id.editText_rentDescription);
        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonSave = view.findViewById(R.id.button_save);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void setButtonAction() {
        //Image button close action to close dialog fragment
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        //Button save action to store rent data
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentDate = editTextDate.getText().toString();
                rentDescription = editTextDescription.getText().toString().trim();

                int flag = 0;

                if (editTextAmount.getText().toString().isEmpty()) {
                    editTextAmount.setError("Please enter rent amount");
                } else {
                    rentAmount = Integer.parseInt(editTextAmount.getText().toString().trim());
                    flag++;
                }

                if (rentDate.isEmpty()) {
                    editTextDate.setError("Please select a date");
                } else {
                    flag++;
                }

                if (flag == 2) {
                    dialogListener.stateChanged(true, rentAmount, rentCategory, rentDescription, rentDate);
                    getDialog().cancel();
                }
            }
        });
    }

    //Spinner function to get Rent Categories
    private void setRentCategorySpinner(View view) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        rentCategoryAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.rentCategories, R.layout.spinner_background_martinique);
        // Specify the layout to use when the list of choices appears
        rentCategoryAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the adapter to the spinner
        spinnerRentCategory.setAdapter(rentCategoryAdapter);
        spinnerRentCategory.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                rentCategory = "House Rent";
                break;
            case 1:
                rentCategory = "Garage Rent";
                break;
            case 2:
                rentCategory = "Electricity Bill";
                break;
            case 3:
                rentCategory = "Gas Bill";
                break;
            case 4:
                rentCategory = "Water Bill";
                break;
            case 5:
                rentCategory = "Garbage Cleaning Bill";
                break;
            case 6:
                rentCategory = "Maid Salary";
                break;
            case 7:
                rentCategory = "Service Charge";
                break;
            case 8:
                rentCategory = "Miscellaneous";
                break;
            case 9:
                rentCategory = "Other";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Show calendar
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(year);
        datePickerDialog.show();
    }

    //Set date according to the picked date from calendar
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        rentDate = new SimpleDateFormat("MMM, yyyy").format(calendar.getTime());

        editTextDate.setText(rentDate);
    }
}
