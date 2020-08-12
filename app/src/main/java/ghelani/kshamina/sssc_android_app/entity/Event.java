package ghelani.kshamina.sssc_android_app.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Event implements Serializable, Comparable<Event>{
    private String id;
    private String name;
    private URL url;
    private String description;
    private Date dateTime;
    private String rawTime;
    private String location;
    private String imageURL;
    private String actionUrl;

    public Event(String id, String name, URL url, String description, Date dateTime, String rawTime, String location, String imageURL, String actionUrl) {
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

    public Event(JSONObject json) {
        try {
            this.id = (String) json.get("id");
            this.name = (String) json.get("name");
            if(json.has("url")) this.url = stringToURL((String) json.get("url"));
            if(json.has("imageURL")) this.imageURL = (String) json.get("imageURL");
            if(json.has("actionUrl")) this.actionUrl = (String) json.get("actionUrl");
            this.description = (String) json.get("description");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'", Locale.CANADA);
            try {
                this.dateTime = format.parse((String) json.get("dateTime"));
            } catch (ParseException e){

            }

            this.rawTime = (String) json.get("rawTime");
            this.location = (String) json.get("location");

        } catch (JSONException e) {
            System.out.println("ERROR2");
        }

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
        } catch (ParseException e){
            System.out.println("Date cannot be parsed.");
        }
        return date;
    }

    public String getDateDisplayString() {
        Calendar cal = Calendar.getInstance(Locale.CANADA);
        cal.setTime(this.dateTime);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        if(day.length() == 1) day = "0" + day;

        return month.substring(0, 3).toUpperCase() + "\n" + day;
    }

    public String getDateDisplayStringSingle() {
        Calendar cal = Calendar.getInstance(Locale.CANADA);
        cal.setTime(this.dateTime);

        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CANADA);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        if(day.length() == 1) day = "0" + day;

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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
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
        } catch(MalformedURLException e) {

        }
        return url;
    }

    @Override public int compareTo(Event event) {
        return getDateTime().compareTo(event.getDateTime());
    }

}