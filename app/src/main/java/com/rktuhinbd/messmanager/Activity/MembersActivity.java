package com.rktuhinbd.messmanager.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rktuhinbd.messmanager.Adapter.MemberListAdapter;
import com.rktuhinbd.messmanager.Database.DatabaseHelper;
import com.rktuhinbd.messmanager.Dialog.AddMemberDialog;
import com.rktuhinbd.messmanager.Listener.AddMemberDialogListener;
import com.rktuhinbd.messmanager.Model.MemberList;
import com.rktuhinbd.messmanager.R;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity implements AddMemberDialogListener {

    private View view;

    private DatabaseHelper databaseHelper;

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

        initiateProperties(view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMember();
            }
        });
    }


    private void initiateProperties(View view) {
        databaseHelper = new DatabaseHelper(view.getContext());

        fab = findViewById(R.id.fab);

        recyclerViewMembers = view.findViewById(R.id.recyclerView_members);
        memberLists = new ArrayList<>();

        initiateRecyclerView(view);
    }


    private void initiateRecyclerView(View view){
        memberLists = databaseHelper.getMemberList();
        memberListAdapter = new MemberListAdapter(view.getContext(), memberLists);

        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMembers.setLayoutManager(layoutManager);
        recyclerViewMembers.setHasFixedSize(true);
        recyclerViewMembers.setAdapter(memberListAdapter);
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
            initiateRecyclerView(view);
        }
    }
}
