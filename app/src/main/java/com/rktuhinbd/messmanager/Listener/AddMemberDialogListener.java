package com.rktuhinbd.messmanager.Listener;

public interface AddMemberDialogListener {
    void stateChanged(boolean updateToken, String name, String Phone, String email, String homeAddress, String nationalId, String occupation, String organisation);
}
