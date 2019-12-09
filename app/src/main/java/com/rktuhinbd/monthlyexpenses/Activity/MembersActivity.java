package com.rktuhinbd.monthlyexpenses.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rktuhinbd.monthlyexpenses.Dialog.AddMemberDialog;
import com.rktuhinbd.monthlyexpenses.Listener.AddMemberDialogListener;
import com.rktuhinbd.monthlyexpenses.R;

public class MembersActivity extends AppCompatActivity implements AddMemberDialogListener {

    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view = getWindow().getDecorView().findViewById(android.R.id.content);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMemberDialog();
            }
        });
    }

    private void addMemberDialog() {
        AddMemberDialog dialog = new AddMemberDialog();
        dialog.setCancelable(true);
        dialog.setDialogListener(this);
        dialog.show(getSupportFragmentManager(), "Add member");
    }

    @Override
    public void stateChanged(boolean updateToken, String name, String phone, String email, String homeAddress, String nationalId) {
        if (updateToken) {
            Log.e("Name", name);
            Log.e("Phone", phone);
            Log.e("Email", email);
            Log.e("Home Address", homeAddress);
            Log.e("National ID", nationalId);
            Snackbar.make(view, "Member added.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}
