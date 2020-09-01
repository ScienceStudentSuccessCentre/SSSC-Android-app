package ghelani.kshamina.sssc_android_app.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class CourseWithAssignmentsAndWeights implements Serializable {

    @Embedded
    public CourseEntity course;

    @Relation(
            parentColumn = "course_id",
            entityColumn = "weight_course_id"
    )
    public List<Weight> weight;

    @Relation(
            parentColumn = "course_id",
            entityColumn = "assignment_course_id",
            entity = Assignment.class
    )
    public List<AssignmentWithWeight> assignments;

    public double calculateGradePercentage() {
        if (assignments.isEmpty()) {
            return -1;
        }

        double totalEarned = 0;
        double totalWeight = 0;
        for (AssignmentWithWeight assignment : assignments) {
            double numAssignmentsWithWeight = assignments.stream()
                    .filter(currAssignment -> currAssignment.assignment.assignmentWeightId.equals(assignment.assignment.assignmentWeightId))
                    .count();
            double calculatedWeight = assignment.weight.weightValue / numAssignmentsWithWeight;

            totalEarned += (assignment.assignment.assignmentGradeEarned / assignment.assignment.assignmentGradeTotal * 100) * calculatedWeight / 100;
            totalWeight += calculatedWeight;
        }

        double percentage = totalEarned / totalWeight * 100;
        return percentage;
    }
}
