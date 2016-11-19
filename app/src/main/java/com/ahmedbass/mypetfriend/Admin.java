package com.ahmedbass.mypetfriend;

public class Admin extends PetOwner {

    public Admin(int userId, String firstName, String lastName, String email, String password, long birthDate, String gender, String city, String country) {
        super(userId, firstName, lastName, email, password, birthDate, gender, city, country);
        setUserType(USER_TYPE_ADMIN);
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
