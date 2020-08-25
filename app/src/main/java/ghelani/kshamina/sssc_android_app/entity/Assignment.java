package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "assignments",
        foreignKeys = @ForeignKey(
                entity = CourseEntity.class,
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
    public String assignmentCourseId;    //foreign key refer to CourseEntity:course_id

    public Assignment() {
    }

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

