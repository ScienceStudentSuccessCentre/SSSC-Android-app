package ghelani.kshamina.sssc_android_app.repository;

import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.Course;

/**
 * Abstraction over accessing the Course DAO through the database directly
 */
public class CourseRepository {
    private GradesDatabase database;

    @Inject
    public CourseRepository(GradesDatabase gradesDatabase) {
        this.database = gradesDatabase;

    }

    public void insertCourse(Course course){
        database.getCourseDao().insertCourse(course);
    }

    public void updateCourse(Course course){
        database.getCourseDao().updateCourse(course);
    }

    public void deleteCourse(Course course){
        database.getCourseDao().deleteCourse(course);
    }

    public  List<Course> getAllCourses(){
        return database.getCourseDao().getAllCourses();
    }

    public  List<Course> getCoursesByID(String id){
        return database.getCourseDao().getCoursesByID(id);
    }

    public  List<Course> getCoursesByTermId(String courseTermId){
        return database.getCourseDao().getCoursesByTermId(courseTermId);
    }

}
