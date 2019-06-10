package ghelani.kshamina.sssc_android_app.event;

public class Event {
    private String event;
    private String date;

    public Event() {

    }

    public Event(String event, String date) {
        this.event = event;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}