package com.rktuhinbd.monthlyexpenses.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rktuhinbd.monthlyexpenses.Listener.AddMemberDialogListener;
import com.rktuhinbd.monthlyexpenses.R;

public class AddMemberDialog extends DialogFragment {

    private AddMemberDialogListener dialogListener;

    public void setDialogListener(AddMemberDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    private EditText editTextName, editTextPhone, editTextEmail, editTextAddress, editTextNationalId;
    private ImageButton imageButtonClose;
    private Button buttonAddMember;

    private String name, phone, email, homeAddress, nationalId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_member, container, false);

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
        imageButtonClose = view.findViewById(R.id.imageButton_close);
        buttonAddMember = view.findViewById(R.id.button_addMember);
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
        dialogListener.stateChanged(true, name, phone, email, homeAddress, nationalId);
        getDialog().cancel();
    }

    private void setImageButtonClose() {
        getDialog().cancel();
    }
}
