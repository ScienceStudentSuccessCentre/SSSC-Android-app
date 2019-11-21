package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "term_id",
        childColumns = "course_term_id"
))

public class Course {
    @PrimaryKey
    @ColumnInfo(name = "course_id")
    public String courseId;  //primary key
    @ColumnInfo(name = "course_name")
    public String courseName;
    @ColumnInfo(name = "course_code")
    public String courseCode;
    @ColumnInfo(name = "course_credits")
    public Double courseCredits;
    @ColumnInfo(name = "course_is_major_courses")
    public Boolean courseIsMajorCourses;
    @ColumnInfo(name = "course_final_grade")
    public String courseFinalGrade;
    @ColumnInfo(name = "course_term_id")
    public String courseTermId;  //foreign key refer to Term:term_id;
}
