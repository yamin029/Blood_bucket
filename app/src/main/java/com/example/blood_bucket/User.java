package com.example.blood_bucket;

import android.net.Uri;

public class User {
    private String userName;
    private String userEmail;
    private String userContactNumber;
    private String userBloodGroup;
    private String userCity;
    private String userAddress;
    private String userImageUri;
    private String userID;

    public User(String userName, String userEmail, String userContactNumber, String userBloodGroup, String userCity, String userAddress, String userImageUri, String userID) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContactNumber = userContactNumber;
        this.userBloodGroup = userBloodGroup;
        this.userCity = userCity;
        this.userAddress = userAddress;
        this.userImageUri = userImageUri;
        this.userID = userID;
    }
    public User() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getUserBloodGroup() {
        return userBloodGroup;
    }

    public void setUserBloodGroup(String userBloodGroup) {
        this.userBloodGroup = userBloodGroup;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserImageUri() {
        return userImageUri;
    }

    public void setUserImageUri(String userImageUri) {
        this.userImageUri = userImageUri;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
