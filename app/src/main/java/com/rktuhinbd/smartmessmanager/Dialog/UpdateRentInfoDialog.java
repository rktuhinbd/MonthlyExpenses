package com.rktuhinbd.smartmessmanager.Dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rktuhinbd.smartmessmanager.Listener.UpdateRentInfoDialogListener;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;

public class UpdateRentInfoDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private UpdateRentInfoDialogListener dialogListener;

    public void setDialogListener(UpdateRentInfoDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private Spinner spinnerOccupation;
    private EditText editTextRentAmount, editTextRentDescription, editTextRentDate;
    private ImageButton imageButtonClose;
    private Button buttonUpdate;

    private String rentId, rentCategory, rentDate, rentDescription;
    private int spinnerSelection, rentPosition, rentAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_expense_info, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        initiateProperties(view);       //Instantiate all properties
        getData();                      //Get data from bundle
        setData();                      //Set data to view properties
        setSpinner(view);               //Spinner initialization and action
        setButtonAction();              //Set button action

        return view;
    }

    //Initiate all properties
    private void initiateProperties(View view) {
        editTextRentAmount = view.findViewById(R.id.editText_rentAmount);
        editTextRentDate = view.findViewById(R.id.editText_rentDate);
        editTextRentDescription = view.findViewById(R.id.editText_description);

        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonUpdate = view.findViewById(R.id.button_updateRentInfo);
    }

    //Get data from bundle
    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            rentPosition = bundle.getInt(Keys.POSITION);
            rentId = bundle.getString(Keys.ID);
            rentCategory = bundle.getString(Keys.RENT_CATEGORY);
            rentAmount = bundle.getInt(Keys.RENT_AMOUNT);
            rentDate = bundle.getString(Keys.RENT_DATE);
            rentDescription = bundle.getString(Keys.RENT_DESCRIPTION);
        }
    }

    //Set data to view
    private void setData() {
        editTextRentAmount.setText(String.valueOf(rentAmount));
        editTextRentDate.setText(rentDate);
        editTextRentDescription.setText(rentDescription);

        switch (rentCategory) {
            case "House Rent":
                spinnerSelection = 0;
                break;
            case "Garage Rent":
                spinnerSelection = 1;
                break;
            case "Electricity Bill":
                spinnerSelection = 2;
                break;
            case "Gas Bill":
                spinnerSelection = 3;
                break;
            case "Water Bill":
                spinnerSelection = 4;
                break;
            case "Garbage Cleaning Bill":
                spinnerSelection = 5;
                break;
            case "Maid Salary":
                spinnerSelection = 6;
                break;
            case "Service Charge":
                spinnerSelection = 7;
                break;
            case "Miscellaneous":
                spinnerSelection = 8;
                break;
            case "Other":
                spinnerSelection = 9;
                break;
        }
    }

    //Spinner function to get Occupation
    private void setSpinner(View view) {
        spinnerOccupation = view.findViewById(R.id.spinner_rentCategories);
        // Create an ArrayAdapter using the string array and a custom spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.rentCategories, R.layout.spinner_background_martinique);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the adapter to the spinner
        spinnerOccupation.setAdapter(spinnerAdapter);
        spinnerOccupation.setSelection(spinnerSelection);
        spinnerOccupation.setOnItemSelectedListener(this);
    }

    //Set button action
    private void setButtonAction() {
        //Image button close action to close dialog fragment
        imageButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });

        //Button save action to store rent data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextRentAmount.getText().toString().isEmpty()) {
                    rentAmount = Integer.parseInt(editTextRentAmount.getText().toString().trim());
                    rentDate = editTextRentDate.getText().toString().trim();
                    rentDescription = editTextRentDescription.getText().toString().trim();

                    dialogListener.stateChanged(true, rentPosition, rentId, rentCategory, rentAmount, rentDate, rentDescription);
                    getDialog().cancel();
                } else {
                    editTextRentAmount.setError("Please enter rent amount");
                }
            }
        });
    }

    //Spinner item selection method
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
        Log.e("Rent category", rentCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
