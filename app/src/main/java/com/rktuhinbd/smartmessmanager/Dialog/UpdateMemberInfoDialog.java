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

import com.rktuhinbd.smartmessmanager.Listener.UpdateMemberInfoDialogListener;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;
import com.rktuhinbd.smartmessmanager.Utility.Validation;

public class UpdateMemberInfoDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private UpdateMemberInfoDialogListener dialogListener;

    public void setDialogListener(UpdateMemberInfoDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    private ArrayAdapter<CharSequence> spinnerAdapter;
    private Spinner spinnerOccupation;
    private EditText editTextName, editTextPhone, editTextEmail, editTextAddress, editTextNationalId, editTextOrganisation;
    private ImageButton imageButtonClose;
    private Button buttonAddMember;

    private String name, phone, mailAddress, homeAddress, nationalId, occupation, organisation;
    private int spinnerSelection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_member_info, container, false);
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
        editTextName = view.findViewById(R.id.editText_name);
        editTextPhone = view.findViewById(R.id.editText_phone);
        editTextEmail = view.findViewById(R.id.editText_email);
        editTextAddress = view.findViewById(R.id.editText_address);
        editTextNationalId = view.findViewById(R.id.editText_nationalId);
        editTextOrganisation = view.findViewById(R.id.editText_organisation);
        spinnerOccupation = new Spinner(getActivity());
        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonAddMember = view.findViewById(R.id.button_updateMemberInfo);
    }

    //Get data from bundle
    private void getData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString(Keys.NAME);
            phone = bundle.getString(Keys.PHONE);
            mailAddress = bundle.getString(Keys.MAIL_ADDRESS);
            homeAddress = bundle.getString(Keys.HOME_ADDRESS);
            nationalId = bundle.getString(Keys.NATIONAL_ID);
            occupation = bundle.getString(Keys.OCCUPATION);
            organisation = bundle.getString(Keys.ORGANISATION);

            switch (occupation) {
                case "Student":
                    spinnerSelection = 0;
                    break;
                case "Job Holder":
                    spinnerSelection = 1;
                    break;
                case "Business":
                    spinnerSelection = 2;
                    break;
                case "Unemployed":
                    spinnerSelection = 3;
                    break;
            }
        }
    }

    //Set data to view
    private void setData() {
        editTextName.setText(name);
        editTextPhone.setText(phone);
        editTextEmail.setText(mailAddress);
        editTextAddress.setText(homeAddress);
        editTextNationalId.setText(nationalId);
        editTextOrganisation.setText(organisation);
    }

    //Spinner function to get Occupation
    private void setSpinner(View view) {
        spinnerOccupation = view.findViewById(R.id.spinner_occupation);
        // Create an ArrayAdapter using the string array and a custom spinner layout
        spinnerAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.occupations, R.layout.spinner_background_martinique);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_background_martinique);
        // Apply the adapter to the spinner
        spinnerOccupation.setAdapter(spinnerAdapter);
        spinnerOccupation.setSelection(spinnerSelection);
        spinnerOccupation.setOnItemSelectedListener(this);
    }

    //Set Add member button and close imageButton action
    private void setButtonAction() {
        buttonAddMember.setOnClickListener(new View.OnClickListener() {     //Add member button press
            @Override
            public void onClick(View v) {       //Add member button action
                name = editTextName.getText().toString().trim();
                phone = editTextPhone.getText().toString().trim();
                mailAddress = editTextEmail.getText().toString().trim();
                homeAddress = editTextAddress.getText().toString().trim();
                nationalId = editTextNationalId.getText().toString().trim();
                organisation = editTextOrganisation.getText().toString().trim();
                setButtonUpdateMemberInfo();
            }
        });

        imageButtonClose.setOnClickListener(new View.OnClickListener() {        //Close dialog imageButton press
            @Override
            public void onClick(View v) {
                getDialog().cancel();
            }
        });
    }

    //Input validation
    private boolean inputValidation(String name, String phone, String email, String homeAddress, String nationalId) {
        int validationFlag = 0;

        if (!Validation.nameValidation(name)) {     //Name validation
            editTextName.setError(Validation.nameErrorMessage);
        } else {
            validationFlag++;
        }

        if (!Validation.phoneValidation(phone)) {       //Phone number validation
            editTextPhone.setError(Validation.phoneErrorMessage);
        } else {
            validationFlag++;
        }

        if (!Validation.emailValidation(email)) {       //Email address validation
            editTextEmail.setError(Validation.emailErrorMessage);
        } else {
            validationFlag++;
        }

        if (!Validation.addressValidation(homeAddress)) {       //Home address validation
            editTextAddress.setError(Validation.addressErrorMessage);
        } else {
            validationFlag++;
        }

        if (!Validation.nationalIdValidation(nationalId)) {       //National ID validation
            editTextNationalId.setError(Validation.nationalIdErrorMessage);
        } else {
            validationFlag++;
        }

        return validationFlag == 5;
    }

    //Add member button's functionality method
    private void setButtonUpdateMemberInfo() {
        if (inputValidation(name, phone, mailAddress, homeAddress, nationalId)) {
            dialogListener.stateChanged(true, name, phone, mailAddress, homeAddress, nationalId, occupation, organisation);
            getDialog().cancel();
        }
    }

    //Spinner item selection method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
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

    //Nothing selected in Spinner method
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
