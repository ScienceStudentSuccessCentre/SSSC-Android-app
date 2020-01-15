package ghelani.kshamina.sssc_android_app.grades.calculator;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Course;
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

    private Thread getCourseData() {
         return new Thread(new Runnable() {
            @Override
            public void run() {
                courseList.clear();

                courseList.add(new Course(
                        "Operating Systems",
                        "COMP 3000",
                        0.5,
                        true,
                        "A+",
                        "F20"
                ));

                courseList.add(new Course(
                        "Operating Systems",
                        "COMP 3000",
                        0.5,
                        false,
                        "A",
                        "F20"
                ));
                // TODO Access the DB

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
