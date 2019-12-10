package com.rktuhinbd.messmanager.Model;

public class MemberList {

    private int memberId;
    private String name, phone, email, homeAddress, nationalId, profilePhotoUrl, occupation, organization;

    public MemberList(int memberId, String name, String phone, String email, String homeAddress, String nationalId, String occupation, String organization, String profilePhotoUrl) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.homeAddress = homeAddress;
        this.nationalId = nationalId;
        this.occupation = occupation;
        this.organization = organization;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getOrganization() {
        return organization;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }
}
