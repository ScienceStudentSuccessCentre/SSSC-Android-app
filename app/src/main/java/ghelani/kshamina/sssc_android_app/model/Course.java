package ghelani.kshamina.sssc_android_app.model;

import androidx.annotation.Nullable;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;

public class Course {

    private String courseId;
    private String courseName;
    private String courseCode;
    private double courseCredits;
    private boolean courseIsMajorCourse;
    private String courseFinalGrade;
    private String courseTermId;

    public Course(){
        this.courseName = "";
        this.courseCode = "";
        this.courseCredits = -1;
        this.courseIsMajorCourse = false;
        this.courseFinalGrade = "";
        this.courseTermId = "";
    }

    public Course(String name, String code, double credits, boolean isMajorCourse, String finalGrade, String termId) {

        this.courseName = name;
        this.courseCode = code;
        this.courseCredits = credits;
        this.courseIsMajorCourse = isMajorCourse;
        this.courseFinalGrade = finalGrade;
        this.courseTermId = termId;
    }

    public Course(CourseEntity courseEntity){
        this.courseId = courseEntity.courseId;
        this.courseName = courseEntity.courseName;
        this.courseCode = courseEntity.courseCode;
        this.courseCredits = courseEntity.courseCredits;
        this.courseIsMajorCourse = courseEntity.courseIsMajorCourse;
        this.courseFinalGrade = courseEntity.courseFinalGrade;
        this.courseTermId = courseEntity.courseTermId;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) return false;
        CourseEntity courseEntity = (CourseEntity) obj;
        return courseId.equals(courseEntity.courseId);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public double getCourseCredits() {
        return courseCredits;
    }

    public void setCourseCredits(double courseCredits) {
        this.courseCredits = courseCredits;
    }

    public boolean isCourseIsMajorCourse() {
        return courseIsMajorCourse;
    }

    public void setCourseIsMajorCourse(boolean courseIsMajorCourse) {
        this.courseIsMajorCourse = courseIsMajorCourse;
    }

    public String getCourseFinalGrade() {
        return courseFinalGrade;
    }

    public void setCourseFinalGrade(String courseFinalGrade) {
        this.courseFinalGrade = courseFinalGrade;
    }

    public String getCourseTermId() {
        return courseTermId;
    }

    public void setCourseTermId(String courseTermId) {
        this.courseTermId = courseTermId;
    }
}
