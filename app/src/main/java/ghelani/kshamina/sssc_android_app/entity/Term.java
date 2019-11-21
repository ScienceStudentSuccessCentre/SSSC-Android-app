package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Term {
    @PrimaryKey
    @ColumnInfo(name = "term_id")
    public String termId;    //primary key
    @ColumnInfo(name = "term_season")
    public String termSeason;
    @ColumnInfo(name = "term_year")
    public String termYear;
}
