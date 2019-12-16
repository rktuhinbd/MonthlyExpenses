package com.rktuhinbd.smartmessmanager.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Dialog.UpdateMemberInfoDialog;
import com.rktuhinbd.smartmessmanager.Listener.UpdateMemberInfoDialogListener;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;

public class MemberInformationActivity extends AppCompatActivity implements UpdateMemberInfoDialogListener {

    private View view;
    private Button buttonRemoveMember, buttonUpdateInformation;
    private ImageView imageViewProfilePhoto;
    private TextView textViewName, textViewPhone, textViewMailAddress, textViewHomeAddress, textViewNationalId, textViewOccupation, textViewOrganisation;

    private DatabaseHelper databaseHelper;

    private String memberId, name, phone, mailAddress, homeAddress, nationalId, occupation, organisation, profilePhotoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information);
        view = getWindow().getDecorView().findViewById(android.R.id.content);

        databaseHelper = new DatabaseHelper(this);

        getData();
        initiateProperties();
        setData();

        setRemoveUser();
        setUpdateMemberInfo();
    }

    private void getData() {
        memberId = getIntent().getStringExtra(Keys.ID);
        name = getIntent().getStringExtra(Keys.NAME);
        phone = getIntent().getStringExtra(Keys.PHONE);
        mailAddress = getIntent().getStringExtra(Keys.MAIL_ADDRESS);
        homeAddress = getIntent().getStringExtra(Keys.HOME_ADDRESS);
        nationalId = getIntent().getStringExtra(Keys.NATIONAL_ID);
        occupation = getIntent().getStringExtra(Keys.OCCUPATION);
        organisation = getIntent().getStringExtra(Keys.ORGANISATION);
        profilePhotoUrl = getIntent().getStringExtra(Keys.PROFILE_PHOTO_URL);
    }

    private void updateData(String name, String phone, String mailAddress, String homeAddress, String nationalId, String occupation, String organisation,
                            String profilePhotoUrl) {
        this.name = name;
        this.phone = phone;
        this.mailAddress = mailAddress;
        this.homeAddress = homeAddress;
        this.nationalId = nationalId;
        this.occupation = occupation;
        this.organisation = organisation;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    private void initiateProperties() {
        buttonRemoveMember = findViewById(R.id.button_removeMember);
        buttonUpdateInformation = findViewById(R.id.button_updateInformation);

        imageViewProfilePhoto = findViewById(R.id.imageView_profilePhoto);
        textViewName = findViewById(R.id.textView_name);
        textViewPhone = findViewById(R.id.textView_phoneNumber);
        textViewMailAddress = findViewById(R.id.textView_mailAddress);
        textViewHomeAddress = findViewById(R.id.textView_homeAddress);
        textViewNationalId = findViewById(R.id.textView_nationalId);
        textViewOccupation = findViewById(R.id.textView_occupation);
        textViewOrganisation = findViewById(R.id.textView_organisation);
    }

    private void setData() {
        if (!profilePhotoUrl.isEmpty()) {
            //Load profile photo url into imageView using Picasso
        } else {
            //Load nothing into imageView
        }

        textViewName.setText(name);
        textViewPhone.setText(phone);
        textViewMailAddress.setText(mailAddress);
        textViewHomeAddress.setText(homeAddress);
        textViewNationalId.setText(nationalId);
        textViewOccupation.setText(occupation);
        textViewOrganisation.setText(organisation);
    }

    private void setRemoveUser() {
        buttonRemoveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Member ID", memberId);
                databaseHelper.removeMember(memberId);
                finish();
            }
        });
    }

    private void setUpdateMemberInfo() {
        buttonUpdateInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMemberInfoDialog();
            }
        });
    }

    private void updateMemberInfoDialog() {
        UpdateMemberInfoDialog dialog = new UpdateMemberInfoDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.NAME, name);
        bundle.putString(Keys.PHONE, phone);
        bundle.putString(Keys.MAIL_ADDRESS, mailAddress);
        bundle.putString(Keys.HOME_ADDRESS, homeAddress);
        bundle.putString(Keys.NATIONAL_ID, nationalId);
        bundle.putString(Keys.OCCUPATION, occupation);
        bundle.putString(Keys.ORGANISATION, organisation);
        dialog.setCancelable(true);
        dialog.setDialogListener(this);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "Update member info");
    }

    @Override
    public void stateChanged(boolean updateToken, String name, String contactNumber, String mailAddress, String homeAddress, String nationalId,
                             String occupation, String organisation) {
        if (updateToken) {
            memberId = getIntent().getStringExtra(Keys.ID);
            databaseHelper.updateMemberInfo(memberId, name, contactNumber, mailAddress, homeAddress, nationalId, occupation, organisation, "");
            updateData(name, contactNumber, mailAddress, homeAddress, nationalId, occupation, organisation, "");
            setData();
            Snackbar.make(view, "Member information updated.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}
