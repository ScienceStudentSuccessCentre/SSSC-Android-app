package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ghelani.kshamina.sssc_android_app.R;

public class WeightFragment extends Fragment {

    private static final String COURSE_ID = "courseID";

    private String courseId;

    public WeightFragment() {
        // Required empty public constructor
    }

    public static WeightFragment newInstance(String courseId) {
        WeightFragment fragment = new WeightFragment();
        Bundle args = new Bundle();
        args.putString(COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courseId = getArguments().getString(COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }
}