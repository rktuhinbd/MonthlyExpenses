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
import com.rktuhinbd.smartmessmanager.Adapter.MemberRecyclerAdapter;
import com.rktuhinbd.smartmessmanager.Database.DatabaseHelper;
import com.rktuhinbd.smartmessmanager.Dialog.AddMemberDialog;
import com.rktuhinbd.smartmessmanager.Listener.AddMemberDialogListener;
import com.rktuhinbd.smartmessmanager.Model.Members;
import com.rktuhinbd.smartmessmanager.R;
import com.rktuhinbd.smartmessmanager.Utility.Keys;
import com.rktuhinbd.smartmessmanager.Utility.SharedPrefs;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity implements AddMemberDialogListener {

    private View view;

    private DatabaseHelper databaseHelper;
    private SharedPrefs sharedPrefs;

    private ArrayList<Members> members;
    private MemberRecyclerAdapter memberRecyclerAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.members);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        view = getWindow().getDecorView().findViewById(android.R.id.content);

        initiateProperties();
        setFab();
        initiateRecyclerViewWithMembers();
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        initiateRecyclerViewWithMembers();
    }


    private void initiateProperties() {
        databaseHelper = new DatabaseHelper(this);
        sharedPrefs = new SharedPrefs(this);

        fab = findViewById(R.id.fab);
        recyclerView = view.findViewById(R.id.recyclerView_members);
        members = new ArrayList<>();
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
        members = databaseHelper.getMemberList();
        sharedPrefs.setSharedPrefDataInt(Keys.MEMBERS, members.size());
        memberRecyclerAdapter = new MemberRecyclerAdapter(this, members);

        Log.e("Member list size", members.size() + "");

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(memberRecyclerAdapter);

        memberRecyclerAdapter.setOnItemClickListener(new MemberRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getBaseContext(), MemberInformationActivity.class);
                intent.putExtra(Keys.ID, members.get(position).getMemberId());
                intent.putExtra(Keys.NAME, members.get(position).getName());
                intent.putExtra(Keys.PHONE, members.get(position).getPhone());
                intent.putExtra(Keys.MAIL_ADDRESS, members.get(position).getEmail());
                intent.putExtra(Keys.HOME_ADDRESS, members.get(position).getHomeAddress());
                intent.putExtra(Keys.NATIONAL_ID, members.get(position).getNationalId());
                intent.putExtra(Keys.OCCUPATION, members.get(position).getOccupation());
                intent.putExtra(Keys.ORGANISATION, members.get(position).getOrganisation());
                intent.putExtra(Keys.PROFILE_PHOTO_URL, members.get(position).getProfilePhotoUrl());
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

            databaseHelper.addMember(name, phone, email, homeAddress, nationalId, occupation, organisation, "");
            Snackbar.make(view, "Member added.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            initiateRecyclerViewWithMembers();
        }
    }
}
