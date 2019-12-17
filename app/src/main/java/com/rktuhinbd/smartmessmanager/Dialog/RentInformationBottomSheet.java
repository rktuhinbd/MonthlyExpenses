package com.rktuhinbd.smartmessmanager.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rktuhinbd.smartmessmanager.Listener.RentInformationDialogListener;
import com.rktuhinbd.smartmessmanager.R;

public class RentInformationBottomSheet extends DialogFragment implements RentInformationDialogListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_rent_information, container, false);

        initiateProperties(view);
        setButtonAction();

        return view;
    }

    private void initiateProperties(View view) {
    }

    private void setButtonAction() {
    }

    @Override
    public void stateChanged(boolean updateToken, int amount, String description, String rentCategory) {

    }
}
