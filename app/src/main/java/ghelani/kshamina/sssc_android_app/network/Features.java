package ghelani.kshamina.sssc_android_app.network;

public class Features {
    private boolean enableEmailEventRegistration;
    private boolean enableEmailMentorRegistration;

    public Features(boolean enableEmailEventRegistration, boolean enableEmailMentorRegistration) {
        this.enableEmailEventRegistration = enableEmailEventRegistration;
        this.enableEmailMentorRegistration = enableEmailMentorRegistration;
    }

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
}
