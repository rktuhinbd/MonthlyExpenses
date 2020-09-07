package com.rktuhinbd.smartmessmanager.Utility;

import android.content.Context;
import android.util.Patterns;

import com.rktuhinbd.smartmessmanager.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static Context mContext;
    public static String nameErrorMessage;
    public static String phoneErrorMessage;
    public static String emailErrorMessage;
    public static String addressErrorMessage;
    public static String nationalIdErrorMessage;

    public Validation(Context context) {
        mContext = context;
    }


    //===== Name validation method =====//

    public static boolean nameValidation(String name) {
        if (name.isEmpty()) {
            nameErrorMessage = mContext.getString(R.string.name_required_);
            return false;
        } else {
            if (name.length() < 3) {
                nameErrorMessage = mContext.getString(R.string.name_length_error_);
                return false;
            } else {
                Pattern namePattern = Pattern.compile("^[ A-Z.,a-z]+$");
                Matcher patternMatcher = namePattern.matcher(name);

                if (patternMatcher.find()) {
                    nameErrorMessage = null;
                    return true;
                } else {
                    nameErrorMessage = mContext.getString(R.string.special_characters_in_name);
                    return false;
                }
            }
        }
    }


    //===== Phone number validation method =====//

    public static boolean phoneValidation(String phoneNumber) {
        phoneNumber = phoneNumber.trim();
        if (phoneNumber.isEmpty()) {
            phoneErrorMessage = mContext.getString(R.string.phone_number_required_);
            return false;
        } else {
            if (phoneNumber.length() != 11) {
                phoneErrorMessage = mContext.getString(R.string.phone_length_error_);
                return false;
            } else {
                if (Patterns.PHONE.matcher(phoneNumber).matches()) {
                    phoneErrorMessage = null;
                    return true;
                } else {
                    phoneErrorMessage = "Invalid phone number!";
                    return false;
                }
            }
        }
    }


    //===== Email address validation method =====//

    public static boolean emailValidation(String email) {
        email = email.trim();
        if (email.isEmpty()) {
            emailErrorMessage = null;
            return true;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailErrorMessage = null;
                return true;
            } else {
                emailErrorMessage = "Invalid email address!";
                return false;
            }
        }
    }


    //===== Address validation method =====//

    public static boolean addressValidation(String address) {
        address = address.trim();
        if (address.isEmpty()) {
            addressErrorMessage = "Address is required!";
            return false;
        } else {
            if (address.length() < 10) {
                addressErrorMessage = "Please provide your full address!";
                return false;
            } else {
                addressErrorMessage = null;
                return true;
            }
        }
    }


    //===== National ID validation method =====//
    
    public static boolean nationalIdValidation(String nationalId) {
        nationalId = nationalId.trim();
        if (nationalId.isEmpty()) {
            nationalIdErrorMessage = "National ID is required!";
            return false;
        } else {
            if (nationalId.length() < 10) {
                nationalIdErrorMessage = "Please check your national id length!";
                return false;
            } else {
                Pattern namePattern = Pattern.compile("^[0-9]*$");
                Matcher patternMatcher = namePattern.matcher(nationalId);
                if (patternMatcher.find()) {
                    nationalIdErrorMessage = null;
                    return true;
                } else {
                    nationalIdErrorMessage = "National ID only accepts number as input!";
                    return false;
                }
            }
        }
    }
}
