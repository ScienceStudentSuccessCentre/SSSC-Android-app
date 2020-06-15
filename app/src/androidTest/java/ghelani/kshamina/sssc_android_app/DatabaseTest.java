package ghelani.kshamina.sssc_android_app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ghelani.kshamina.sssc_android_app.database.AssignmentDao;
import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.database.WeightDao;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.entity.Weight;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private TermDao termDao;
    private CourseDao courseDao;
    private AssignmentDao assignmentDao;
    private WeightDao weightDao;
    private GradesDatabase db;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        db = Room.inMemoryDatabaseBuilder(context, GradesDatabase.class).build();
        termDao = db.getTermDao();
        courseDao = db.getCourseDao();
        assignmentDao = db.getAssignmentDao();
        weightDao = db.getWeightDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void createAndDeleteTerms() {
        Term term1 = new Term(Term.Season.WINTER, "2020");
        Term term2 = new Term(Term.Season.FALL, "2020");
        Course course1 = new Course("Test Course", "TEST1001", 0.5, true, "None", term1.termId);
        Course course2 = new Course("Another Test Course", "TEST4001", 0.5, false, "A-", term2.termId);

        //--- Insert all of the data ---//
        termDao.insertTerm(term1);
        termDao.insertTerm(term2);
        courseDao.insertCourse(course1);
        courseDao.insertCourse(course2);

        List<Term> terms = termDao.getAllTerms();
        assertEquals(terms.size(), 2);

        List<Course> allCourses = courseDao.getAllCourses();
        assertEquals(allCourses.size(), 2);

        List<Course> coursesByTermId = courseDao.getCoursesByTermId(term1.termId);
        assertEquals(coursesByTermId.size(), 1);
        assertEquals(coursesByTermId.get(0), course1);

        //--- Delete term1, should delete course1 but not course2 ---//
        termDao.deleteTerm(term1);

        terms = termDao.getAllTerms();
        assertEquals(terms.size(), 1);
        assertEquals(terms.get(0), term2);

        allCourses = courseDao.getAllCourses();
        assertEquals(allCourses.size(), 1);
        assertEquals(allCourses.get(0), course2);

        coursesByTermId = courseDao.getCoursesByTermId(term1.termId);
        assertEquals(coursesByTermId.size(), 0);
    }

    @Test
    public void createAndDeleteCourses() {
        Term term = new Term(Term.Season.WINTER, "2020");
        Course course1 = new Course("Test Course", "TEST1001", 0.5, true, "None", term.termId);
        Course course2 = new Course("Another Test Course", "TEST4001", 0.5, false, "A-", term.termId);
        Weight weight1 = new Weight("TEST1001 Work", 100, course1.courseId);
        Weight weight2 = new Weight("TEST4001 Work", 100, course2.courseId);
        Assignment assignment1 = new Assignment("Final Exam", 65, 80, weight1.weightId, course1.courseId);
        Assignment assignment2 = new Assignment("Final Exam", 90, 100, weight2.weightId, course2.courseId);

        //--- Insert all of the data ---//
        termDao.insertTerm(term);
        courseDao.insertCourse(course1);
        courseDao.insertCourse(course2);
        weightDao.insertWeight(weight1);
        weightDao.insertWeight(weight2);
        assignmentDao.insertAssignment(assignment1);
        assignmentDao.insertAssignment(assignment2);

        List<Course> allCourses = courseDao.getAllCourses();
        assertEquals(allCourses.size(), 2);

        List<Course> coursesByTermId = courseDao.getCoursesByTermId(term.termId);
        assertEquals(coursesByTermId.size(), 2);

        List<Course> coursesByCourseId = courseDao.getCoursesByID(course1.courseId);
        assertEquals(coursesByCourseId.size(), 1);
        assertEquals(coursesByCourseId.get(0), course1);

        List<Weight> allWeights = weightDao.getAllWeights();
        assertEquals(allWeights.size(), 2);

        List<Weight> weightsByCourseId = weightDao.getWeightsByCourseId(course1.courseId);
        assertEquals(weightsByCourseId.size(), 1);
        assertEquals(weightsByCourseId.get(0), weight1);

        List<Assignment> allAssignments = assignmentDao.getAllAssignments();
        assertEquals(allAssignments.size(), 2);

        List<Assignment> assignmentsByCourseId = assignmentDao.getAssignmentsByCourseId(course1.courseId);
        assertEquals(assignmentsByCourseId.size(), 1);
        assertEquals(assignmentsByCourseId.get(0), assignment1);

        //--- Delete course1, should delete weight1 and assignment1 but not weight2 and assignment2 ---//
        courseDao.deleteCourse(course1);

        allCourses = courseDao.getAllCourses();
        assertEquals(allCourses.size(), 1);
        assertEquals(allCourses.get(0), course2);

        coursesByTermId = courseDao.getCoursesByTermId(term.termId);
        assertEquals(coursesByTermId.size(), 1);
        assertEquals(coursesByTermId.get(0), course2);

        coursesByCourseId = courseDao.getCoursesByID(course1.courseId);
        assertEquals(coursesByCourseId.size(), 0);

        allWeights = weightDao.getAllWeights();
        assertEquals(allWeights.size(), 1);
        assertEquals(allWeights.get(0), weight2);

        weightsByCourseId = weightDao.getWeightsByCourseId(course1.courseId);
        assertEquals(weightsByCourseId.size(), 0);

        allAssignments = assignmentDao.getAllAssignments();
        assertEquals(allAssignments.size(), 1);
        assertEquals(allAssignments.get(0), assignment2);

        assignmentsByCourseId = assignmentDao.getAssignmentsByCourseId(course1.courseId);
        assertEquals(assignmentsByCourseId.size(), 0);
    }
}