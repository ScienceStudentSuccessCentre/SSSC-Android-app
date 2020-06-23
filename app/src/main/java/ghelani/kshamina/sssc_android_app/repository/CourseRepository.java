package ghelani.kshamina.sssc_android_app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.model.Course;
import io.reactivex.Single;

/**
 * Abstraction over accessing the CourseEntity DAO through the database directly
 */
public class CourseRepository {
    private GradesDatabase database;

    @Inject
    public CourseRepository(GradesDatabase gradesDatabase) {
        this.database = gradesDatabase;

    }

    public void insertCourse(CourseEntity courseEntity){
        database.getCourseDao().insertCourse(courseEntity);
    }

    public void updateCourse(CourseEntity courseEntity){
        database.getCourseDao().updateCourse(courseEntity);
    }

    public void deleteCourse(CourseEntity courseEntity){
        database.getCourseDao().deleteCourse(courseEntity);
    }

    public  List<CourseEntity> getAllCourses(){
        return database.getCourseDao().getAllCourses();
    }

    public  List<CourseEntity> getCoursesByID(String id){
        return database.getCourseDao().getCoursesByID(id);
    }

    public Single<List<Course>> getCoursesByTermId(String courseTermId){
        return database.getCourseDao().getCoursesByTermId(courseTermId).flatMap(courseEntities -> {
                    List<Course> courseList = new ArrayList<>();
                    for(CourseEntity courseEntity: courseEntities) {
                        courseList.add(new Course(courseEntity));
                    }
                    return Single.just(courseList);
                }
        );
    }

}
