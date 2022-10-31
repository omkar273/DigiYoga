package desno.hackathon.omkar.digiyoga.ModalClass;

public class UserProfile {

    private String USER_Uid;
    private String USER_Display_Name;
    private String USER_Phone;
    private String USER_Email;
    private String USER_Dob;
    private String USER_Password;
    private String USER_Profile_Image_URl;

    public UserProfile(String USER_Uid, String USER_Display_Name, String USER_Phone, String USER_Email, String USER_Dob, String USER_Password, String USER_Profile_Image_URl) {
        this.USER_Uid = USER_Uid;
        this.USER_Display_Name = USER_Display_Name;
        this.USER_Phone = USER_Phone;
        this.USER_Email = USER_Email;
        this.USER_Dob = USER_Dob;
        this.USER_Password = USER_Password;
        this.USER_Profile_Image_URl = USER_Profile_Image_URl;
    }

    public UserProfile() {
    }

    public UserProfile(String USER_Uid, String USER_Display_Name, String USER_Phone, String USER_Email, String USER_Dob, String USER_Password) {
        this.USER_Uid = USER_Uid;
        this.USER_Display_Name = USER_Display_Name;
        this.USER_Phone = USER_Phone;
        this.USER_Email = USER_Email;
        this.USER_Dob = USER_Dob;
        this.USER_Password = USER_Password;
    }

    public String getUSER_Uid() {
        return USER_Uid;
    }

    public void setUSER_Uid(String USER_Uid) {
        this.USER_Uid = USER_Uid;
    }

    public String getUSER_Display_Name() {
        return USER_Display_Name;
    }

    public void setUSER_Display_Name(String USER_Display_Name) {
        this.USER_Display_Name = USER_Display_Name;
    }

    public String getUSER_Phone() {
        return USER_Phone;
    }

    public void setUSER_Phone(String USER_Phone) {
        this.USER_Phone = USER_Phone;
    }

    public String getUSER_Email() {
        return USER_Email;
    }

    public void setUSER_Email(String USER_Email) {
        this.USER_Email = USER_Email;
    }

    public String getUSER_Dob() {
        return USER_Dob;
    }

    public void setUSER_Dob(String USER_Dob) {
        this.USER_Dob = USER_Dob;
    }

    public String getUSER_Password() {
        return USER_Password;
    }

    public void setUSER_Password(String USER_Password) {
        this.USER_Password = USER_Password;
    }

    public String getUSER_Profile_Image_URl() {
        return USER_Profile_Image_URl;
    }

    public void setUSER_Profile_Image_URl(String USER_Profile_Image_URl) {
        this.USER_Profile_Image_URl = USER_Profile_Image_URl;
    }
}
