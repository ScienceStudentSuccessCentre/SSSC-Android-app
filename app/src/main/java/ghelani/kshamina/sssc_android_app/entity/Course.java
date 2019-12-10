package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity(foreignKeys = @ForeignKey(
        entity = Term.class,
        parentColumns = "term_id",
        childColumns = "course_term_id",
        onDelete = ForeignKey.CASCADE
))
public class Course {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "course_id")
    public String courseId;  //primary key
    @ColumnInfo(name = "course_name")
    public String courseName;
    @ColumnInfo(name = "course_code")
    public String courseCode;
    @ColumnInfo(name = "course_credits")
    public double courseCredits;
    @ColumnInfo(name = "course_is_major_course")
    public boolean courseIsMajorCourse;
    @ColumnInfo(name = "course_final_grade")
    public String courseFinalGrade;
    @ColumnInfo(name = "course_term_id")
    public String courseTermId;  //foreign key refer to Term:term_id;

    public Course() {}

    public Course(String name, String code, double credits, boolean isMajorCourse, String finalGrade, String termId) {
        courseId = UUID.randomUUID().toString();
        this.courseName = name;
        this.courseCode = code;
        this.courseCredits = credits;
        this.courseIsMajorCourse = isMajorCourse;
        this.courseFinalGrade = finalGrade;
        this.courseTermId = termId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Course course = (Course) obj;
        return courseId.equals(course.courseId);
    }

    @Override
    public int hashCode() {
        return 31 + courseId.hashCode();
    }
}
