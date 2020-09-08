package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "terms")
public class TermEntity implements Comparable<TermEntity>, Serializable {

    public enum Season {
        FALL(1),
        WINTER(2),
        SUMMER(3);

        private final int seasonValue;

        Season(int seasonValue) {
            this.seasonValue = seasonValue;
        }

        public int getSeasonValue() {
            return seasonValue;
        }
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "term_id")
    public String termId;    //primary key
    @ColumnInfo(name = "term_season")
    public String termSeason;
    @ColumnInfo(name = "term_year")
    public String termYear;

    public TermEntity() {
    }

    public TermEntity(Season season, String year) {
        termId = UUID.randomUUID().toString();
        this.termSeason = season.toString();
        this.termYear = year;
    }

    @Override
    public int compareTo(TermEntity term) {

        int result = Integer.compare(Integer.parseInt(this.getTermYear()), Integer.parseInt(term.getTermYear()));
        if (result != 0) {
            return result;
        } else {
            return Integer.compare(Season.valueOf(this.getTermSeason()).getSeasonValue(), Season.valueOf(term.getTermSeason()).getSeasonValue());
        }

    }

    public String asShortString() {
        return
                this.termSeason.charAt(0) +
                        (this.termYear.length() == 4 ? this.termYear.substring(2, 4) : this.termYear);
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        TermEntity term = (TermEntity) obj;
        return termId.equals(term.getTermId());
    }

    @Override
    public int hashCode() {
        return 31 + termId.hashCode();
    }

    @Override
    public String toString() {
        return this.termSeason.charAt(0) + this.termSeason.substring(1).toLowerCase() + " " + this.termYear;
    }

    @NonNull
    public String getTermId() {
        return termId;
    }

    public void setTermId(@NonNull String termId) {
        this.termId = termId;
    }

    public String getTermSeason() {
        return termSeason;
    }

    public void setTermSeason(String termSeason) {
        this.termSeason = termSeason;
    }

    public String getTermYear() {
        return termYear;
    }

    public void setTermYear(String termYear) {
        this.termYear = termYear;
    }
}
