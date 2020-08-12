package ghelani.kshamina.sssc_android_app.network;

public class Features {
    private boolean enableEmailEventRegistration;

    public Features(boolean enableEmailEventRegistration) {
        this.enableEmailEventRegistration = enableEmailEventRegistration;
    }

    public boolean isEnableEmailEventRegistration() {
        return enableEmailEventRegistration;
    }

    public void setEnableEmailEventRegistration(boolean enableEmailEventRegistration) {
        this.enableEmailEventRegistration = enableEmailEventRegistration;
    }
}
