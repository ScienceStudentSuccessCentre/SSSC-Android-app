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

    public void setCourses_code(String courses_code) {
        this.courses_code = courses_code;
    }

    public void setCourses_credits(Double courses_credits) {
        this.courses_credits = courses_credits;
    }

    public void setCourses_finalGrade(String courses_finalGrade) {
        this.courses_finalGrade = courses_finalGrade;
    }

    public void setCourses_id(String courses_id) {
        this.courses_id = courses_id;
    }

    public void setCourses_isMajor_Courses(Boolean courses_isMajor_Courses) {
        this.courses_isMajor_Courses = courses_isMajor_Courses;
    }

    public void setCourses_name(String courses_name) {
        this.courses_name = courses_name;
    }

    public void setCourses_termId(String courses_termId) {
        this.courses_termId = courses_termId;
    }

    public Boolean getCourses_isMajor_Courses() {
        return courses_isMajor_Courses;
    }

    public Double getCourses_credits() {
        return courses_credits;
    }

    public String getCourses_code() {
        return courses_code;
    }

    public String getCourses_finalGrade() {
        return courses_finalGrade;
    }

    public String getCourses_id() {
        return courses_id;
    }

    public String getCourses_name() {
        return courses_name;
    }

    public String getCourses_termId() {
        return courses_termId;
    }
}
