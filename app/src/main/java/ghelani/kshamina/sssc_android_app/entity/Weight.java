package ghelani.kshamina.sssc_android_app.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.UUID;

@Entity(foreignKeys = @ForeignKey(
        entity = Course.class,
        parentColumns = "course_id",
        childColumns = "weight_course_id",
        onDelete = ForeignKey.CASCADE
))
public class Weight {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "weight_id") //maybe can't add this line here
    public String weightId;  //primary key
    @ColumnInfo(name = "weight_name")
    public String weightName;
    @ColumnInfo(name = "weight_value")
    public double weightValue;
    @ColumnInfo(name = "weight_course_id")
    public String weightCourseId;    //foreign key refer to Course:course_id

    public Weight() {}

    public Weight(String name, double value, String courseId) {
        weightId = UUID.randomUUID().toString();
        this.weightName = name;
        this.weightValue = value;
        this.weightCourseId = courseId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Weight weight = (Weight) obj;
        return weightId.equals(weight.weightId);
    }

    @Override
    public int hashCode() {
        return 31 + weightId.hashCode();
    }
}
