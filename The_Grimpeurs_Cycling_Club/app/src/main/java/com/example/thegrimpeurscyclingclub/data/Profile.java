package com.example.thegrimpeurscyclingclub.data;

public class Profile {
    private String socialMediaLink;
    private String contactName;
    private String phoneNumber;

    private String userID;
    private String role;

    public Profile(String userID,String role){
        this.userID=userID;
        this.role=role;
        socialMediaLink=null;
        contactName=null;
        phoneNumber=null;

    }

    public void setContactName(String contactName) {
        if(contactName.equals("")||contactName.equals("Please Enter Your Contact Name")){
            this.contactName="N/A";
        }
        else{
            this.contactName = contactName;
        }
    }

    public void setPhoneNumber(String phoneNumber) {

        if(phoneNumber.equals("")||phoneNumber.equals("Please Enter Your Phone Number (mandatory)")){
            this.phoneNumber="N/A";
        }
        else{
            this.phoneNumber = phoneNumber;
        }
    }

    public void setSocialMediaLink(String socialMediaLink) {
        if(socialMediaLink.equals("")||socialMediaLink.equals("Please Enter Your Social Media Link (mandatory)")){
            this.socialMediaLink="N/A";
        }
        else{
            this.socialMediaLink = socialMediaLink;
        }
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public String getRole() {
        return role;
    }

    public String getUserID() {
        return userID;
    }
    public boolean validate(){
        if (this.socialMediaLink.equals("N/A")){
            return false;
        }
        if(this.phoneNumber.equals("N/A")){
            return false;
        }
        return true;

    }
}
