package com.rktuhinbd.smartmessmanager.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Dialog.UpdateMemberInfoDialog;
import com.rktuhinbd.smartmessmanager.Listener.UpdateMemberInfoDialogListener;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;

public class MemberInformationActivity extends AppCompatActivity implements UpdateMemberInfoDialogListener {
    public static final int READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1;
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
        getMemberPhoto();
        setRemoveUser();
        setUpdateMemberInfo();
    }

    private void getMemberPhoto() {
        imageViewProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MemberInformationActivity.this, "Yay! Kachumbor permission already granted.", Toast.LENGTH_SHORT).show();
                } else {
                    requestedPermissionGranted();
                }
            }
        });
    }

    private void requestedPermissionGranted() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("External storage read permission")
                    .setMessage("External storage reading permission is needed to set profile photo of member")
                    .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MemberInformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    READ_EXTERNAL_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Kachumbor eating is permitted", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("External storage read permission")
                        .setMessage("You blocked permission request, if you are willing to grant storage reading permission you can grant it from the permission setting" +
                                " of the application")
                        .setPositiveButton("Take Me There", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Redirect the app setting for modification of the permissions
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        }
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
