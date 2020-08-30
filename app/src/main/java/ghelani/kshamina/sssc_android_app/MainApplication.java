package ghelani.kshamina.sssc_android_app;


import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
@HiltAndroidApp
public class MainApplication extends Application {

    private boolean enableEmailEventRegistration = true;
    private boolean enableEmailMentorRegistration = true;
    private String studentName = "";
    private String studentId = "";
    private String email  = "";


    public boolean isEnableEmailEventRegistration() {
        return enableEmailEventRegistration;
    }

    public void setEnableEmailEventRegistration(boolean enableEmailEventRegistration) {
        this.enableEmailEventRegistration = enableEmailEventRegistration;
    }

    public boolean isEnableEmailMentorRegistration() {
        return enableEmailMentorRegistration;
    }

    public void setEnableEmailMentorRegistration(boolean enableEmailMentorRegistration) {
        this.enableEmailMentorRegistration = enableEmailMentorRegistration;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean hasStudentInformation(){
        return !studentId.isEmpty() && !studentName.isEmpty() && !email.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
