package com.example.hci;

public class UserAccount {
    public UserAccount() {
    }

    private String name;
    private String idToken;
    private String emailId;
    private String birthday;
    private String phoneNumber;
    private String password;
    private String gender;
    private String maritalStatus;

    public String getIdToken() {return idToken; }
    public void setIdToken(String idToken) {this.idToken = idToken; }

    public String getEmailId() {return emailId; }
    public void setEmailId(String emailId) {this.emailId = emailId; }

    private String getPassword() {return password; }
    public void setPassword(String password) {this.password = password; }

    public String getBirthday() {return birthday; }
    public void setBirthday(String birthday) {this.birthday = birthday; }

    public String getPhoneNumber() {return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber; }

    public String getName() {return name; }
    public void setName(String name) {this.name = name; }

    public String getGender() {return gender; }
    public void setGender(String gender) {this.gender = gender; }

    public String getMaritalStatus() {return maritalStatus;}

    public void setMaritalStatus(String maritalStatus) {this.maritalStatus = maritalStatus;}
}

