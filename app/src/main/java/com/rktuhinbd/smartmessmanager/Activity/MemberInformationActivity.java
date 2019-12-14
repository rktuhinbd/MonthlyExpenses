package com.rktuhinbd.smartmessmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rktuhinbd.smartmessmanager.Model.MemberList;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Constants;

import java.util.ArrayList;

public class MemberInformationActivity extends AppCompatActivity {

    private Button buttonRemoveMember, buttonUpdateInformation;
    private ImageView imageViewProfilePhoto;
    private TextView textViewName, textViewPhone, textViewMailAddress, textViewHomeAddress, textViewNationalId, textViewOccupation, textViewOrganisation;

    private ArrayList<MemberList> memberLists;

    private String name, phone, mailAddress, homeAddress, nationalId, occupation, organisation, profilePhotoUrl;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_information);

        getData();
        initiateProperties();
        setData();

        setRemoveUser();
    }

    private void getData() {
        memberLists = new ArrayList<>();

        position = getIntent().getIntExtra(Constants.POSITION, -1);
        name = getIntent().getStringExtra(Constants.NAME);
        phone = getIntent().getStringExtra(Constants.PHONE);
        mailAddress = getIntent().getStringExtra(Constants.EMAIL);
        homeAddress = getIntent().getStringExtra(Constants.HOME_ADDRESS);
        nationalId = getIntent().getStringExtra(Constants.NATIONAL_ID);
        occupation = getIntent().getStringExtra(Constants.OCCUPATION);
        organisation = getIntent().getStringExtra(Constants.ORGANISATION);
        profilePhotoUrl = getIntent().getStringExtra(Constants.PROFILE_PHOTO_URL);

        Log.e("Position", position + "");
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
        if(!profilePhotoUrl.isEmpty()){
            //Load profile photo url into imageView using Picasso
        }else{
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
//                memberLists.remove(position);
                finish();
            }
        });
    }
}
