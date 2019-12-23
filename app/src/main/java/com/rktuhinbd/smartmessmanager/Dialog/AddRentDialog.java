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

import com.rktuhinbd.smartmessmanager.Listener.AddRentDialogListener;
import com.rktuhinbd.smartmessmanager.R;

public class AddRentDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText editTextAmount, editTextDescription;
    private ImageButton imageButtonClose;
    private Button buttonSave;

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private String rentDescription, rentCategory;
    private int rentAmount;

    private AddRentDialogListener dialogListener;

    public void setDialogListener(AddRentDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_rent, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        initiateProperties(view);
        setSpinner(view);
        setButtonAction();

        return view;
    }

    private void initiateProperties(View view) {
        spinner = view.findViewById(R.id.spinner_rentCategories);
        editTextAmount = view.findViewById(R.id.editText_rentAmount);
        editTextDescription = view.findViewById(R.id.editText_description);
        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonSave = view.findViewById(R.id.button_save);
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
                if (!editTextAmount.getText().toString().isEmpty()) {
                    rentAmount = Integer.parseInt(editTextAmount.getText().toString().trim());
                    rentDescription = editTextDescription.getText().toString().trim();

                    dialogListener.stateChanged(true, rentAmount, rentCategory, rentDescription);
                    getDialog().cancel();
                } else {
                    editTextAmount.setError("Please enter rent amount");
                }
            }
        });
    }


    //Spinner function to get Rent Categories
    private void setSpinner(View view) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.rentCategories, R.layout.spinner_background_martinique);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
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
        Log.e("Rent category", rentCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
