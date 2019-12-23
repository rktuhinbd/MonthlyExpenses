package com.rktuhinbd.smartmessmanager.Listener;

public interface UpdateRentInfoDialogListener {
    void stateChanged(boolean updateToken, int rentPosition, String rentId, String rentCategory, int rentAmount, String rentDate, String rentDescription);
}
