package com.rktuhinbd.smartmessmanager.Model;

public class Rents {

    private int rentAmount;
    private String rentId, rentCategory, rentDescription, rentMonth;

    public Rents(String rentId, int rentAmount, String rentCategory, String rentDescription, String rentMonth) {
        this.rentId = rentId;
        this.rentAmount = rentAmount;
        this.rentCategory = rentCategory;
        this.rentDescription = rentDescription;
        this.rentMonth = rentMonth;
    }

    public int getRentAmount() {
        return rentAmount;
    }

    public String getRentId() {
        return rentId;
    }

    public String getRentCategory() {
        return rentCategory;
    }

    public String getRentDescription() {
        return rentDescription;
    }

    public String getRentMonth() {
        return rentMonth;
    }
}
