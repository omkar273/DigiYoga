package desno.hackathon.omkar.digiyoga.ModalClass;

public class YogaWorkoutPlans {

    String Yoga_Plan_Description, Yoga_Plan_Estimated_Days, Yoga_Plan_Image_Url, Yoga_Plan_Name;

    public YogaWorkoutPlans() {
    }

    public YogaWorkoutPlans(String yoga_Plan_Description, String yoga_Plan_Estimated_Days, String yoga_Plan_Image_Url, String yoga_Plan_Name) {
        Yoga_Plan_Description = yoga_Plan_Description;
        Yoga_Plan_Estimated_Days = yoga_Plan_Estimated_Days;
        Yoga_Plan_Image_Url = yoga_Plan_Image_Url;
        Yoga_Plan_Name = yoga_Plan_Name;
    }

    public String getYoga_Plan_Description() {
        return Yoga_Plan_Description;
    }

    public void setYoga_Plan_Description(String yoga_Plan_Description) {
        Yoga_Plan_Description = yoga_Plan_Description;
    }

    public String getYoga_Plan_Estimated_Days() {
        return Yoga_Plan_Estimated_Days;
    }

    public void setYoga_Plan_Estimated_Days(String yoga_Plan_Estimated_Days) {
        Yoga_Plan_Estimated_Days = yoga_Plan_Estimated_Days;
    }

    public String getYoga_Plan_Image_Url() {
        return Yoga_Plan_Image_Url;
    }

    public void setYoga_Plan_Image_Url(String yoga_Plan_Image_Url) {
        Yoga_Plan_Image_Url = yoga_Plan_Image_Url;
    }

    public String getYoga_Plan_Name() {
        return Yoga_Plan_Name;
    }

    public void setYoga_Plan_Name(String yoga_Plan_Name) {
        Yoga_Plan_Name = yoga_Plan_Name;
    }
}
