package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class AssignmentWithWeight {

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
