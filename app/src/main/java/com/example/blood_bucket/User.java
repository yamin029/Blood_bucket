package com.example.blood_bucket;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
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

    protected User(Parcel in) {
        userName = in.readString();
        userEmail = in.readString();
        userContactNumber = in.readString();
        userBloodGroup = in.readString();
        userCity = in.readString();
        userAddress = in.readString();
        userImageUri = in.readString();
        userID = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userContactNumber);
        parcel.writeString(userBloodGroup);
        parcel.writeString(userCity);
        parcel.writeString(userAddress);
        parcel.writeString(userImageUri);
        parcel.writeString(userID);
    }
}
