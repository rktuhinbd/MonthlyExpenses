package com.rktuhinbd.smartmessmanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rktuhinbd.smartmessmanager.Adapter.MemberListAdapter;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Dialog.AddMemberDialog;
import com.rktuhinbd.smartmessmanager.Listener.AddMemberDialogListener;
import com.rktuhinbd.smartmessmanager.Model.MemberList;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Constants;
import com.rktuhinbd.smartmessmanager.Utility.SharedPrefs;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity implements AddMemberDialogListener {

    private View view;

    private DatabaseHelper databaseHelper;
    private SharedPrefs sharedPrefs;

    private ArrayList<MemberList> memberLists;
    private MemberListAdapter memberListAdapter;
    private RecyclerView recyclerViewMembers;
    private LinearLayoutManager layoutManager;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view = getWindow().getDecorView().findViewById(android.R.id.content);

        initiateProperties();
        setFab();
        initiateRecyclerViewWithMembers();
    }


    private void initiateProperties() {
        databaseHelper = new DatabaseHelper(this);
        sharedPrefs = new SharedPrefs(this);

        fab = findViewById(R.id.fab);
        recyclerViewMembers = view.findViewById(R.id.recyclerView_members);
        memberLists = new ArrayList<>();
    }


    private void setFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });
    }


    private void initiateRecyclerViewWithMembers() {
        memberLists = databaseHelper.getMemberList();
        sharedPrefs.setSharedPrefDataInt(SharedPrefs.MEMBERS, memberLists.size());
        memberListAdapter = new MemberListAdapter(this, memberLists);

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMembers.setLayoutManager(layoutManager);
        recyclerViewMembers.setHasFixedSize(true);
        recyclerViewMembers.setAdapter(memberListAdapter);

        memberListAdapter.setOnItemClickListener(new MemberListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), MemberInformationActivity.class);
                intent.putExtra(Constants.POSITION, position);
                intent.putExtra(Constants.NAME, memberLists.get(position).getName());
                intent.putExtra(Constants.PHONE, memberLists.get(position).getPhone());
                intent.putExtra(Constants.EMAIL, memberLists.get(position).getEmail());
                intent.putExtra(Constants.HOME_ADDRESS, memberLists.get(position).getHomeAddress());
                intent.putExtra(Constants.NATIONAL_ID, memberLists.get(position).getNationalId());
                intent.putExtra(Constants.OCCUPATION, memberLists.get(position).getOccupation());
                intent.putExtra(Constants.ORGANISATION, memberLists.get(position).getOrganisation());
                intent.putExtra(Constants.PROFILE_PHOTO_URL, memberLists.get(position).getProfilePhotoUrl());
                Log.e("Organisation", memberLists.get(position).getOrganisation());
                startActivity(intent);
            }
        });
    }


    private void addMember() {
        AddMemberDialog dialog = new AddMemberDialog();
        dialog.setCancelable(true);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), "Add member");
    }


    @Override
    public void stateChanged(boolean updateToken, String name, String phone, String email, String homeAddress, String nationalId, String occupation, String organisation) {
        if (updateToken) {
            Log.e("Name", name);
            Log.e("Phone", phone);
            Log.e("Email", email);
            Log.e("Home Address", homeAddress);
            Log.e("National ID", nationalId);
            Log.e("Occupation", occupation);
            Log.e("Organisation", organisation);
            databaseHelper.addMember(name, phone, email, homeAddress, nationalId, occupation, organisation, "");
            Snackbar.make(view, "Member added.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            initiateRecyclerViewWithMembers();
        }
    }
}
