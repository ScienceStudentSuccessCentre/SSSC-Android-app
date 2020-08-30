package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;

public class AssignmentWithWeight implements Serializable {

    @Embedded
    public Assignment assignment;

    @Relation(
            parentColumn = "assignment_weight_id",
            entityColumn = "weight_id"
    )
    public Weight weight;

    public Assignment getAssignment() {
        return assignment;
    }
}
