package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity
public class Term {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "term_id")
    public String termId;    //primary key
    @ColumnInfo(name = "term_season")
    public String termSeason;
    @ColumnInfo(name = "term_year")
    public String termYear;

    public Term() {}

    public Term(String season, String year) {
        termId = UUID.randomUUID().toString();
        this.termSeason = season;
        this.termYear = year;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Term term = (Term) obj;
        return termId.equals(term.termId);
    }

    @Override
    public int hashCode() {
        return 31 + termId.hashCode();
    }
}