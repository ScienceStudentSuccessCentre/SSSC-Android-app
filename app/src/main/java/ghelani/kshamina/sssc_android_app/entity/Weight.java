package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_id",
        childColumns = "weight_course_id"
))
public class Weight {
    @PrimaryKey
    @ColumnInfo(name = "weight_id") //maybe can't add this line here
    public String weightId;  //primary key
    @ColumnInfo(name = "weight_name")
    public String weightName;
    @ColumnInfo(name = "weight_value")
    public Double weightValue;
    @ColumnInfo(name = "weight_course_id")
    public String weightCourseId;    //foreign key refer to Course:course_id
}

