package ghelani.kshamina.sssc_android_app.model;

import androidx.annotation.Nullable;

/**
 * A domain model for term classes coming from the local database
 */
public class Term implements Comparable<Term> {

    public enum Season {
        WINTER(1),
        SUMMER(2),
        FALL(3);

        private final int seasonValue;

        Season(int seasonValue) {
            this.seasonValue = seasonValue;
        }

        public int getSeasonValue() {
            return seasonValue;
        }
    }

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
    @Override
    public String toString() {
        return this.season.charAt(0)+ this.season.substring(1).toLowerCase()  + " " + this.year;
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

    public String getDisplayableSeason() {
        return season.charAt(0)+ this.season.substring(1).toLowerCase();
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

    public String asShortString() {
        return
                this.season.toString().charAt(0) +
                        (this.year.length() == 4 ? this.year.substring(2, 4) : this.year);
    }

    @Override
    public int compareTo(Term term) {
        int result = Integer.compare(Integer.parseInt(this.getYear()), Integer.parseInt(term.year));
        if(result != 0) {
            return result;
        }else{
            return Integer.compare(Season.valueOf(this.getSeason()).getSeasonValue(), Season.valueOf(term.getSeason()).getSeasonValue());
        }
    }

}
