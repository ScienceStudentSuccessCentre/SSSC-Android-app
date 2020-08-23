package ghelani.kshamina.sssc_android_app;


import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import ghelani.kshamina.sssc_android_app.dagger.DaggerAppComponent;

public class MainApplication extends DaggerApplication {

    private boolean enableEmailEventRegistration = true;
    private boolean enableEmailMentorRegistration = true;
    private String studentName = "";
    private String studentId = "";
    private String degree  = "";


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
        return !studentId.isEmpty() && !studentName.isEmpty() && !degree.isEmpty();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
