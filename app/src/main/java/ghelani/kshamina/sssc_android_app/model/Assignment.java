package ghelani.kshamina.sssc_android_app.model;

public class Assignment {
    private String assignments_id;  //primary key
    private String assignments_name;
    private Double assignments_gradeEarned;
    private Double assignments_gradeTotal;
    private String assignments_weightId;    //foreign key refer to Weight:weights_id
    private String assignments_courseId;    //foreign key refer to Course:course_id

    //I'll leave a default constructor here in case someone wants to add all his assignments in advance
    public Assignment(){
    //system.out.println("Don't add empty assignments you stupid :(\n");
    }

    //yet another constructor
    public Assignment(String id, String name, Double gradeEarned, Double gradeTotal, String weightId, String courseId){
        this.assignments_courseId = id;
        this.assignments_gradeEarned = gradeEarned;
        this.assignments_gradeTotal = gradeTotal;
        this.assignments_courseId = courseId;
        this.assignments_name = name;
        this.assignments_weightId = weightId;
    }

}

