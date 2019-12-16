package com.rktuhinbd.smartmessmanager.Listener;

public interface UpdateMemberInfoDialogListener {
    void stateChanged(boolean updateToken, String name, String Phone, String email, String homeAddress, String nationalId, String occupation, String organisation);
}
