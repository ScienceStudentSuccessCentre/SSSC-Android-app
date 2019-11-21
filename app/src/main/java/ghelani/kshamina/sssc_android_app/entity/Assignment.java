package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Weight.class,
                parentColumns = "weight_id",
                childColumns = "assignment_weight_id",
                onDelete = CASCADE
        ),
        @ForeignKey(
                entity = Weight.class,
                parentColumns = "weight_id",
                childColumns = "assignment_weight_id",
                onDelete = CASCADE
        )
})

public class Assignment {
    @PrimaryKey
    @ColumnInfo(name = "assignment_id")
    public String assignmentId;  //primary key
    @ColumnInfo(name = "assignment_name")
    public String assignmentName;
    @ColumnInfo(name = "assignment_grade_earned")
    public Double assignmentGradeEarned;
    @ColumnInfo(name = "assignment_grade_total")
    public Double assignmentGradeTotal;
    @ColumnInfo(name = "assignment_weight_id")
    public String assignmentWeightId;    //foreign key refer to Weight:weights_id
    @ColumnInfo(name = "asssignment_course_id")
    public String assignmentCourseId;    //foreign key refer to Course:course_id
}

