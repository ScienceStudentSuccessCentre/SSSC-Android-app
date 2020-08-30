package ghelani.kshamina.sssc_android_app.entity;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;

public class Event implements Serializable, Comparable<Event>, DiffItem {
    private String id;
    private String name;
    private String url;
    private String description;
    private Date dateTime;
    private String rawTime;
    private String location;
    private String imageURL;
    private String actionUrl;

    public Event(String id, String name, String url, String description, Date dateTime, String rawTime, String location, String imageURL, String actionUrl) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
        this.dateTime = dateTime;
        this.rawTime = rawTime;
        this.location = location;
        this.imageURL = imageURL;
        this.actionUrl = actionUrl;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
     *   Convert string dateTime format from https://sssc.carleton.ca/events to Date
     *   Format: Wednesday, September 18, 2019
     */
    public static Date stringToDate(String stringDate) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("E, MMM dd, yyyy").parse(stringDate);
        } catch (ParseException e) {
            System.out.println("Date cannot be parsed.");
        }
        return date;
    }

    public String getDateDisplayString() {
        Calendar cal = Calendar.getInstance(Locale.CANADA);
        cal.setTime(this.dateTime);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1) day = "0" + day;
        return month.substring(0, 3) + "\n" + day;
    }

    public String getDateDisplayStringSingle() {
        Calendar cal = Calendar.getInstance(Locale.CANADA);
        cal.setTime(this.dateTime);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        if (day.length() == 1) day = "0" + day;

        return month.substring(0, 3) + " " + day;
    }

    public long getNotificationTime() {
        Calendar cal = Calendar.getInstance(Locale.CANADA);
        cal.setTime(this.dateTime);

        // subtract an hour for notification time
        cal.add(Calendar.HOUR, -1);
        return cal.getTimeInMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return "https://sssc.carleton.ca" + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRawTime() {
        return rawTime;
    }

    public void setRawTime(String rawTime) {
        this.rawTime = rawTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public URL stringToURL(String string) {
        URL url = null;
        try {
            url = new URL("https://sssc.carleton.ca" + string);
        } catch (MalformedURLException ignored) {

        }
        return url;
    }

    @Override
    public int compareTo(Event event) {
        return getDateTime().compareTo(event.getDateTime());
    }

}