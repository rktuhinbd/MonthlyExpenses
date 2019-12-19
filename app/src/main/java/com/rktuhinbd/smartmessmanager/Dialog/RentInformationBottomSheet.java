package com.rktuhinbd.smartmessmanager.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rktuhinbd.smartmessmanager.R;

public class RentInformationBottomSheet extends BottomSheetDialogFragment {

    private TextView textViewRentCategory, textViewRentAmount, textViewRentDescription;
    private Button buttonUpdateRentInformation;

    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_rent_information, container, false);

        initiateProperties(view);
        setButtonAction();

        return view;
    }

    private void initiateProperties(View view) {
        textViewRentCategory = view.findViewById(R.id.textView_rentCategory);
        textViewRentAmount = view.findViewById(R.id.textView_rentAmount);
        textViewRentDescription = view.findViewById(R.id.textView_rentDescription);

        buttonUpdateRentInformation = view.findViewById(R.id.button_updateRentInformation);
    }

    private void setButtonAction() {
        buttonUpdateRentInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetListener.onBottomSheetItemClick("Rent information updated");
            }
        });
    }

    public interface BottomSheetListener {
        void onBottomSheetItemClick(String key);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Rent info bottom sheet listener");
        }
    }
}
