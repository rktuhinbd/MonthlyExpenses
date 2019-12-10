package com.rktuhinbd.messmanager.Dialog;

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

import com.rktuhinbd.messmanager.Database.DatabaseHelper;
import com.rktuhinbd.messmanager.Listener.AddMemberDialogListener;
import com.rktuhinbd.messmanager.R;

public class AddMemberDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private AddMemberDialogListener dialogListener;

    public void setDialogListener(AddMemberDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private Spinner spinnerOccupation;
    private EditText editTextName, editTextPhone, editTextEmail, editTextAddress, editTextNationalId, editTextOrganisation;
    private ImageButton imageButtonClose;
    private Button buttonAddMember;

    private String name, phone, email, homeAddress, nationalId, occupation, organisation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_member, container, false);

        setSpinner(view);               //Spinner initialization and action
        initiateProperties(view);       //Instantiate all properties
        setButtonAction();              //Set button action

        return view;
    }


    private void initiateProperties(View view) {
        editTextName = view.findViewById(R.id.editText_name);
        editTextPhone = view.findViewById(R.id.editText_phone);
        editTextEmail = view.findViewById(R.id.editText_email);
        editTextAddress = view.findViewById(R.id.editText_address);
        editTextNationalId = view.findViewById(R.id.editText_nationalId);
        editTextOrganisation = view.findViewById(R.id.editText_organisation);
        spinnerOccupation = new Spinner(getActivity());
        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonAddMember = view.findViewById(R.id.button_addMember);
    }


    private void setSpinner(View view){         //Spinner function to get Occupation
        spinnerOccupation = view.findViewById(R.id.spinner_occupation);
        // Create an ArrayAdapter using the string array and a default spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.occupations, R.layout.spinner_background_martinique);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the adapter to the spinner
        spinnerOccupation.setAdapter(spinnerAdapter);
        spinnerOccupation.setOnItemSelectedListener(this);
    }


    private void setButtonAction() {
        buttonAddMember.setOnClickListener(new View.OnClickListener() {     //Add member button action
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString().trim();
                phone = editTextPhone.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                homeAddress = editTextAddress.getText().toString().trim();
                nationalId = editTextNationalId.getText().toString().trim();
                organisation = editTextOrganisation.getText().toString().trim();
                setButtonAddMember();
            }
        });

        imageButtonClose.setOnClickListener(new View.OnClickListener() {        //Dialog close button action
            @Override
            public void onClick(View v) {
                setImageButtonClose();
            }
        });
    }


    private void setButtonAddMember() {
        dialogListener.stateChanged(true, name, phone, email, homeAddress, nationalId, occupation, organisation);
        getDialog().cancel();
    }


    private void setImageButtonClose() {
        getDialog().cancel();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                occupation = "Student";
                break;
            case 1:
                occupation = "Job Holder";
                break;
            case 2:
                occupation = "Business";
                break;
            case 3:
                occupation = "Unemployed";
                break;
        }
        Log.e("Occupation", occupation);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
