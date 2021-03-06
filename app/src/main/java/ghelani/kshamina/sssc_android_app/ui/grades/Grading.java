package ghelani.kshamina.sssc_android_app.ui.grades;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;

public class Grading {

    public static final TreeMap<Integer, String> gradeToLetter = new TreeMap<>();
    static {
        gradeToLetter.put(0,"F");
        gradeToLetter.put(50,"D-");
        gradeToLetter.put(53,"D");
        gradeToLetter.put(57,"D+");
        gradeToLetter.put(60,"C-");
        gradeToLetter.put(63,"C");
        gradeToLetter.put(67,"C+");
        gradeToLetter.put(70,"B-");
        gradeToLetter.put(73,"B");
        gradeToLetter.put(77,"B+");
        gradeToLetter.put(80,"A-");
        gradeToLetter.put(85,"A");
        gradeToLetter.put(90,"A+");
    }

    public static final HashMap<String, Integer> letterGradeToGPA = new HashMap<>();
    static {
        letterGradeToGPA.put("F", 0);
        letterGradeToGPA.put("D-", 1);
        letterGradeToGPA.put("D", 2);
        letterGradeToGPA.put("D+", 3);
        letterGradeToGPA.put("C-", 4);
        letterGradeToGPA.put("C", 5);
        letterGradeToGPA.put("C+", 6);
        letterGradeToGPA.put("B-", 7);
        letterGradeToGPA.put("B", 8);
        letterGradeToGPA.put("B+", 9);
        letterGradeToGPA.put("A-", 10);
        letterGradeToGPA.put("A", 11);
        letterGradeToGPA.put("A+", 12);
    }

    public static double calculateRequiredCGPA(double currentGPA, double creditsComplete, double desiredGPA, double creditsInProgress) {
        return (desiredGPA * (creditsInProgress + creditsComplete) - currentGPA * creditsComplete) / creditsInProgress;
    }

    public static double calculatePredictedCGPA(double currentGPA, double creditsComplete, double predictedGPA, double creditsInProgress) {
        return ((currentGPA * creditsComplete) + (predictedGPA * creditsInProgress)) / (creditsComplete + creditsInProgress);
    }

    public static double calculateOverallCGPA(List<CourseWithAssignmentsAndWeights> courseEntity) {
        double totalGradePoints = 0;
        double totalCreditsWithGrades = 0;

        for (CourseWithAssignmentsAndWeights course : courseEntity) {
            Integer gpa = letterGradeToGPA.get(course.getCourseLetterGrade());
            double gradeWeight = (gpa == null) ? -1 : gpa * course.course.courseCredits;

            if (gradeWeight >= 0) {
                totalGradePoints += gradeWeight;
                totalCreditsWithGrades += course.course.courseCredits;
            }
        }

        if (totalCreditsWithGrades > 0) {
            return (totalGradePoints / totalCreditsWithGrades);
        }
        return -1;
    }
    public static double calculateTermGPA(List<CourseWithAssignmentsAndWeights> courses) {
        double totalGradePoints = 0;
        double totalCreditsWithGrades = 0;

        for (CourseWithAssignmentsAndWeights course : courses) {
            CourseEntity courseData = course.course;
            Integer gpa = letterGradeToGPA.get(courseData.courseFinalGrade);
            double gradeWeight = (gpa == null) ? -1 : gpa * courseData.courseCredits;

            if (gradeWeight >= 0) {
                totalGradePoints += gradeWeight;
                totalCreditsWithGrades += courseData.courseCredits;
            }
        }

        if (totalCreditsWithGrades > 0) {
            return (totalGradePoints / totalCreditsWithGrades);
        }
        return -1;
    }

}
