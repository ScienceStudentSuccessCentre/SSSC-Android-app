package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_id",
        childColumns = "assignment_course_id",
        onDelete = ForeignKey.CASCADE
))
public class Assignment {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "assignment_id")
    public String assignmentId;  //primary key
    @ColumnInfo(name = "assignment_name")
    public String assignmentName;
    @ColumnInfo(name = "assignment_grade_earned")
    public double assignmentGradeEarned;
    @ColumnInfo(name = "assignment_grade_total")
    public double assignmentGradeTotal;
    @ColumnInfo(name = "assignment_weight_id")
    public String assignmentWeightId;    //foreign key refer to Weight:weights_id
    @ColumnInfo(name = "assignment_course_id")
    public String assignmentCourseId;    //foreign key refer to Course:course_id

    public Assignment() {}

    public Assignment(String name, double gradeEarned, double gradeTotal, String weightId, String courseId) {
        assignmentId = UUID.randomUUID().toString();
        this.assignmentName = name;
        this.assignmentGradeEarned = gradeEarned;
        this.assignmentGradeTotal = gradeTotal;
        this.assignmentWeightId = weightId;
        this.assignmentCourseId = courseId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Assignment assignment = (Assignment) obj;
        return assignmentId.equals(assignment.assignmentId);
    }

    @Override
    public int hashCode() {
        return 31 + assignmentId.hashCode();
    }
}

