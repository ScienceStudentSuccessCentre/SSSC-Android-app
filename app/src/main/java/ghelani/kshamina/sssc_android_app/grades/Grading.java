package ghelani.kshamina.sssc_android_app.grades;

public class Grading {
    public static double calculateRequiredCGPA(double currentGPA, double creditsComplete, double desiredGPA, double creditsInProgress) {
        return (desiredGPA * (creditsInProgress + creditsComplete) - currentGPA * creditsComplete) / creditsInProgress;
    }
}
