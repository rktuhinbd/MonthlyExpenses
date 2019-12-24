package com.rktuhinbd.smartmessmanager.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Listener.UpdateRentInfoDialogListener;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;

public class RentInformationBottomSheet extends BottomSheetDialogFragment implements UpdateRentInfoDialogListener {

    private TextView textViewRentCategory, textViewRentAmount, textViewRentDate, textViewRentDescription;
    private Button buttonUpdateRentInfo, buttonRemoveRentInfo;

    private DatabaseHelper databaseHelper;
    private String rentId, rentCategory, rentDate, rentDescription;
    private int rentPosition, rentAmount;

    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_rent_information, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initiateProperties(view);
        getRentData();
        setRentData();
        setButtonAction();

        return view;
    }

    private void getRentData() {                                                        //Get data from bundle
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            rentPosition = this.getArguments().getInt(Keys.POSITION);
            rentId = this.getArguments().getString(Keys.ID);
            rentCategory = this.getArguments().getString(Keys.RENT_CATEGORY);
            rentAmount = this.getArguments().getInt(Keys.RENT_AMOUNT);
            rentDate = this.getArguments().getString(Keys.RENT_DATE);
            rentDescription = this.getArguments().getString(Keys.RENT_DESCRIPTION);
        }
    }

    private void setRentData() {                                                         //Set data to properties
        textViewRentCategory.setText(rentCategory);
        textViewRentAmount.setText(String.valueOf(rentAmount));
        textViewRentDate.setText(rentDate);
        if (!rentDescription.isEmpty()) {
            textViewRentDescription.setText(rentDescription);
            textViewRentDescription.setVisibility(View.VISIBLE);
        } else {
            textViewRentDescription.setVisibility(View.GONE);
        }
    }

    private void initiateProperties(View view) {                                        //Initiate properties
        databaseHelper = new DatabaseHelper(getActivity());
        textViewRentCategory = view.findViewById(R.id.textView_rentCategory);
        textViewRentAmount = view.findViewById(R.id.textView_rentAmount);
        textViewRentDate = view.findViewById(R.id.textView_rentDate);
        textViewRentDescription = view.findViewById(R.id.textView_rentDescription);

        buttonRemoveRentInfo = view.findViewById(R.id.button_removeRentInfo);
        buttonUpdateRentInfo = view.findViewById(R.id.button_updateRentInfo);
    }

    //Button action
    private void setButtonAction() {
        buttonRemoveRentInfo.setOnClickListener(new View.OnClickListener() {            //Remove rent information
            @Override
            public void onClick(View v) {
                databaseHelper.removeRentInformation(rentId);
                Toast.makeText(getActivity(), "Rent information removed", Toast.LENGTH_SHORT).show();
                bottomSheetListener.onBottomSheetItemClick("removed", rentPosition);
                getDialog().cancel();
            }
        });

        buttonUpdateRentInfo.setOnClickListener(new View.OnClickListener() {            //Update rent information
            @Override
            public void onClick(View v) {
                updateRentInfoDialog();
            }
        });
    }


    private void updateRentInfoDialog() {
        UpdateRentInfoDialog dialog = new UpdateRentInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Keys.POSITION, rentPosition);
        bundle.putString(Keys.ID, rentId);
        bundle.putString(Keys.RENT_CATEGORY, rentCategory);
        bundle.putInt(Keys.RENT_AMOUNT, rentAmount);
        bundle.putString(Keys.RENT_DATE, rentDate);
        bundle.putString(Keys.RENT_DESCRIPTION, rentDescription);
        dialog.setCancelable(true);
        dialog.setDialogListener(this);
        dialog.setArguments(bundle);
        dialog.show(getActivity().getSupportFragmentManager(), "Update rent info");
    }


    @Override
    public void stateChanged(boolean updateToken, int rentPosition, String rentId, String rentCategory, int rentAmount, String rentDate, String rentDescription) {
        if(updateToken){
            //Update data to set in view properties
            this.rentPosition = rentPosition;
            this.rentId = rentId;
            this.rentCategory = rentCategory;
            this.rentAmount = rentAmount;
            this.rentDate = rentDate;
            this.rentDescription = rentDescription;

            //Update rent information data to database table
            databaseHelper.updateRentInfo(rentId, rentCategory, rentAmount, rentDate, rentDescription);
            //Set updated data to view properties
            setRentData();

            Toast.makeText(getActivity(), "Rent information updated", Toast.LENGTH_SHORT).show();
            bottomSheetListener.onBottomSheetItemClick("updated", rentPosition);
            getDialog().cancel();
        }
    }

    public interface BottomSheetListener {
        void onBottomSheetItemClick(String key, int position);                                        //Pass bottom sheet listener key
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
