package desno.hackathon.omkar.digiyoga.ModalClass;

public class SavedUserProfile {

    private String USER_Display_Name;
    private String USER_Profile_Image_URl;
    private String USER_UID_KEY;

    public SavedUserProfile(String USER_Display_Name, String USER_Profile_Image_URl, String user_uid_key) {

        this.USER_Display_Name = USER_Display_Name;
        this.USER_Profile_Image_URl = USER_Profile_Image_URl;
        USER_UID_KEY = user_uid_key;
    }

    public SavedUserProfile() {
    }

    public String getUSER_UID_KEY() {
        return USER_UID_KEY;
    }

    public void setUSER_UID_KEY(String USER_UID_KEY) {
        this.USER_UID_KEY = USER_UID_KEY;
    }

    public String getUSER_Display_Name() {
        return USER_Display_Name;
    }

    public void setUSER_Display_Name(String USER_Display_Name) {
        this.USER_Display_Name = USER_Display_Name;
    }

    public String getUSER_Profile_Image_URl() {
        return USER_Profile_Image_URl;
    }

    public void setUSER_Profile_Image_URl(String USER_Profile_Image_URl) {
        this.USER_Profile_Image_URl = USER_Profile_Image_URl;
    }
}
