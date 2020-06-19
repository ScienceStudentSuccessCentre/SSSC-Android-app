package ghelani.kshamina.sssc_android_app.model;

import androidx.annotation.Nullable;

public class Term {
    public enum Season {SUMMER, WINTER, FALL}

    private String id;
    private String season;
    private String year;

    public Term(String id, String season, String year) {
        this.id = id;
        this.season = season;
        this.year = year;
    }
    public Term(String season, String year) {
        this.id = "";
        this.season = season;
        this.year = year;
    }



    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Term term = (Term) obj;
        return id.equals(term.id);
    }

    @Override
    public int hashCode() {
        return 31 + id.hashCode();
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
