package ghelani.kshamina.sssc_android_app.event;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Event implements Serializable {
    private String event;
    private Date date;
    private String description;

    public Event() {

    }

    public Event(String event, Date date, String description) {
        this.event = event;
        this.date = date;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
    *   Convert string date format from https://sssc.carleton.ca/events to Date
    *   Format: Wednesday, September 18, 2019
     */
    public static Date stringToDate(String stringDate) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("E, MMM dd, yyyy").parse(stringDate);
        } catch (ParseException e){
            System.out.println("Date cannot be parsed.");
        }
        return date;
    }

    public String getDateDisplayString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        if(day.length() == 1) day = "0" + day;

        return month.substring(0, 3) + " " + day;
    }
}