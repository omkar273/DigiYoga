package desno.hackathon.omkar.digiyoga.Constants;

public class Constants {

    //request codes
    public static final int GOOGLE_SIGN_IN_REQUEST_CODE = 10001;
    public static final int PROFILE_IMAGE_UPDATE_REQUEST_CODE = 10002;


    // firebase realtime database keys
    public static final String USER_PROFILE_KEY = "USER_Profile";

    // user profile keys
    public static final String USER_PROFILE_DISPLAY_NAME_KEY = "USER_Display_Name";
    public static final String USER_PROFILE_EMAIL_KEY = "USER_Email";
    public static final String USER_PROFILE_PHONE_KEY = "USER_Phone";
    public static final String USER_PROFILE_DOB_KEY = "USER_Dob";
    public static final String USER_PROFILE_IMAGE_URL_KEY = "USER_Profile_Image_URl";


    //User details
    public static final String USER_PASSWORD_KEY = "USER_Password";
    public static final String USER_UID_KEY = "USER_Uid";


    //section for yoga plans section
    public static final String YOGA_WORKOUT_SECTION = "Yoga_Workout_section";

    // firebase realtime database yoga workout plan keys
    public static final String YOGA_PLAN_NAME = "Yoga_Plan_Name";
    public static final String YOGA_PLAN_DESCRIPTION = "Yoga_Plan_Description";
    public static final String YOGA_PLAN_ESTIMATED_DAYS = "Yoga_Plan_Estimated_Days";
    public static final String YOGA_PLAN_IMAGE_URL = "Yoga_Plan_Image_Url";

    //yoga types
    public static final String YOGA_TYPE_NAME = "Beginner_Yoga_Plan";

    //Yoga Homepage Quote
    public static final String YOGA_QUOTE_NAME = "Yoga Quotes";


    //keys for fetching data in home fragment
    public static final String USER_DISPLAY_NAME_KEY = "UserName";
    public static final String HOMEPAGE_YOGA_QUOTE = "YogaQuote";

    //differencent sections in realtime databases
    public static final String USERS_DETAILS_KEY = "USERS";
    public static final String USERS_DP_STORAGE_SECTION_KEY = "User_Profile_pics";
    public static final String SAVED_USERS_SECTION_KEY = "Saved_Users";

}
