package ghelani.kshamina.sssc_android_app.ui.common.model;

import ghelani.kshamina.sssc_android_app.ui.common.list.DiffItem;

public class TermItem implements DiffItem {
    private String id;
    private String season;
    private String year;

    public TermItem(String id, String season, String year) {
        this.id = id;
        this.season = season;
        this.year = year;
    }

    public String asShortString() {
        return
                this.season.toString().charAt(0) +
                        (this.year.length() == 4 ? this.year.substring(2, 4) : this.year);
    }

    @Override
    public String toString() {
        return this.season + " " + this.year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
