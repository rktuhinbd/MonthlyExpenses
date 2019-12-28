package com.rktuhinbd.smartmessmanager.Listener;

public interface AddExpenseDialogListener {
    void stateChanged(boolean updateToken, int amount, String description, String rentCategory);
}
