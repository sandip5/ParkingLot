package com.bridgelabz.parkinglot.model;

public class SecurityStaff {
    public String redirect(boolean checkFullSign) {
        if(checkFullSign)
            return "Lot Is Full, So Redirect Security";
        return "Lot Is Not Full";
    }
}
