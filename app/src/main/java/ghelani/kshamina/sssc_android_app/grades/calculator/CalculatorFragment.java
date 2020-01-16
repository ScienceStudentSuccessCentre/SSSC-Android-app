package ghelani.kshamina.sssc_android_app.grades.calculator;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ghelani.kshamina.sssc_android_app.DAO.CourseDao;
import ghelani.kshamina.sssc_android_app.DAO.TermDao;
import ghelani.kshamina.sssc_android_app.GradesDatabase;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Course;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.grades.Grading;

public class CalculatorFragment extends Fragment {

    private TextView calculatedOverallCGPA;
    private TextView calculatedMajorCGPA;

    private List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CoursesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        ViewGroup calculatorView = (ViewGroup) inflater.inflate(R.layout.fragment_calculator, container,false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerView = calculatorView.findViewById(R.id.coursesList);
        recyclerView.setHasFixedSize(true);

        adapter = new CoursesAdapter(courseList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(onItemClickListener);  TODO Add action listener

        calculatedOverallCGPA = calculatorView.findViewById(R.id.calculatorCalculatedOverallCGPA);
        calculatedMajorCGPA = calculatorView.findViewById(R.id.calculatorCalculatedMajorCGPA);

        Thread thread = getCourseData();

        try {
            thread.start();
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateCGPAs();

        return calculatorView;
    }

    /**
     * Returns a Thread. When run, it populates courseList with the contents of the database
     *     and updates the RecyclerView.
     */
    private Thread getCourseData() {
         return new Thread(new Runnable() {
            @Override
            public void run() {
                courseList.clear();

                Term dummyTerm = new Term("F", "19");  // To satisfy foreign key constraint
                Course dummyCourse1 = new Course(
                        "Operating Systems",
                        "COMP 3000",
                        0.5,
                        true,
                        "A+",
                        dummyTerm.termId
                );
                Course dummyCourse2 = new Course(
                        "Operating Systems",
                        "COMP 3000",
                        0.5,
                        false,
                        "A",
                        dummyTerm.termId
                );

                GradesDatabase db = GradesDatabase.getInstance(getActivity());
                TermDao termDao = db.getTermDao();
                CourseDao courseDao = db.getCourseDao();

                termDao.insertTerm(dummyTerm);
                courseDao.insertCourse(dummyCourse1);
                courseDao.insertCourse(dummyCourse2);

                courseList.addAll(courseDao.getAllCourses());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void updateCGPAs() {
        double overallCGPA = Grading.calculateOverallCGPA(courseList);
        calculatedOverallCGPA.setText(String.format(Locale.CANADA, "Overall CGPA: %.1f", overallCGPA));

        List<Course> majorCourses = new ArrayList<>();
        for (Course course : courseList) {
            if (course.courseIsMajorCourse) majorCourses.add(course);
        }

        double overallMajorCGPA = Grading.calculateOverallCGPA(majorCourses);
        calculatedMajorCGPA.setText(String.format(Locale.CANADA, "Major CGPA: %.1f", overallMajorCGPA));
    }

}
