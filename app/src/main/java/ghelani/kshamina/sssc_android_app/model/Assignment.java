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

    public void setAssignments_id(String assignments_id) {
        this.assignments_id = assignments_id;
    }

    public void setAssignments_gradeEarned(Double assignments_gradeEarned) {
        this.assignments_gradeEarned = assignments_gradeEarned;
    }

    public void setAssignments_name(String assignments_name) {
        this.assignments_name = assignments_name;
    }

    public void setAssignments_gradeTotal(Double assignments_gradeTotal) {
        this.assignments_gradeTotal = assignments_gradeTotal;
    }

    public void setAssignments_weightId(String assignments_weightId) {
        this.assignments_weightId = assignments_weightId;
    }

    public void setAssignments_courseId(String assignments_courseId) {
        this.assignments_courseId = assignments_courseId;
    }

    public Double getAssignments_gradeEarned() {
        return assignments_gradeEarned;
    }

    public Double getAssignments_gradeTotal() {
        return assignments_gradeTotal;
    }

    public String getAssignments_id() {
        return assignments_id;
    }

    public String getAssignments_name() {
        return assignments_name;
    }

    public String getAssignments_courseId() {
        return assignments_courseId;
    }

    public String getAssignments_weightId() {
        return assignments_weightId;
    }
}

