package ghelani.kshamina.sssc_android_app.grades;

import java.util.HashMap;
import java.util.List;

import ghelani.kshamina.sssc_android_app.entity.Course;

public class Grading {

    public static HashMap<String, Integer> letterGradeToGPA = new HashMap<>();
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

    public static double calculateOverallCGPA(List<Course> courses) {
        double totalGradePoints = 0;
        double totalCreditsWithGrades = 0;

        System.out.println("List : " + courses);

        for (Course course : courses) {
            Integer gpa = letterGradeToGPA.get(course.courseFinalGrade);
            System.out.println("Grade: " + gpa);
            double gradeWeight = (gpa == null) ? -1 : gpa * course.courseCredits;

            if (gradeWeight >= 0) {
                totalGradePoints += gradeWeight;
                totalCreditsWithGrades += course.courseCredits;
            }
        }

        System.out.println("asdfa " + totalGradePoints + " : " + totalCreditsWithGrades);


        if (totalCreditsWithGrades > 0) {
            return (totalGradePoints / totalCreditsWithGrades);
        }
        return -1;
    }

}
