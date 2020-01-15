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

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Course;

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

        calculatedOverallCGPA.setText("123123123");  // TODO display actual CGPAs
        calculatedMajorCGPA.setText("123123123");

        getCourseData();

        return calculatorView;

    }

    private void getCourseData() {
        new Thread(new Runnable() {
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
                // TODO Access the DB

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

}
