package ghelani.kshamina.sssc_android_app.model;

public class Course {
    private String courses_id;  //primary key
    private String courses_name;
    private String courses_code;
    private Double courses_credits;
    private Boolean courses_isMajor_Courses;
    private String courses_finalGrade;
    private String courses_termId;  //foreign key refer to Term:term_id;

    //if user don't know the final grade
    public Course(String id, String name, String code, Double credits, Boolean isMajor, String termId){
        this.courses_code = code;
        this.courses_credits = credits;
        this.courses_id = id;
        this.courses_isMajor_Courses = isMajor;
        this.courses_termId = termId;
        this.courses_name = name;
    }

    //user know all the information now!
    public Course(String id, String name, String code, Double credits, Boolean isMajor, String finalGrade, String termId){
        this.courses_code = code;
        this.courses_credits = credits;
        this.courses_finalGrade = finalGrade;
        this.courses_id = id;
        this.courses_isMajor_Courses = isMajor;
        this.courses_termId = termId;
        this.courses_name = name;
    }
}
