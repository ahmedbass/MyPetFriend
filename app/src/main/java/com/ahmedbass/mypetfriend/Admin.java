package com.ahmedbass.mypetfriend;

public class Admin extends PetOwner {

    public Admin(int userId, long createDate, String userType, String firstName, String lastName, String email, String password, long birthDate, String gender, String country, String city, String phone, String profilePhoto) {
        super(userId, createDate, userType, firstName, lastName, email, password, birthDate, gender, country, city, phone, profilePhoto);
    }

    public void viewAllUsers() {

    }

    public void deleteUser() {

    }

    public void suspendUser() {

    }

    public void reactivateUser() {

    }

    public void sendAdminMessage() {

    }
}
