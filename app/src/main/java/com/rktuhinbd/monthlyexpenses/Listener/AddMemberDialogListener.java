package com.rktuhinbd.monthlyexpenses.Listener;

public interface AddMemberDialogListener {
    void stateChanged(boolean updateToken, String name, String Phone, String email, String homeAddress, String nationalId);
}
