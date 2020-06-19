package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.UUID;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.model.Term.Season;

@Entity(tableName = "terms")
public class TermEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "term_id")
    public String termId;    //primary key
    @ColumnInfo(name = "term_season")
    public String termSeason;
    @ColumnInfo(name = "term_year")
    public String termYear;

    public TermEntity(){}

    public TermEntity(Season season, String year) {
        termId = UUID.randomUUID().toString();
        this.termSeason = season.toString();
        this.termYear = year;
    }

    public TermEntity(Term term){
        this.termId = term.getId();
        this.termSeason = term.getSeason();
        this.termYear = term.getYear();
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
