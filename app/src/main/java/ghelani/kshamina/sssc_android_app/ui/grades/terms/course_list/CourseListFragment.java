package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ghelani.kshamina.sssc_android_app.R;

public class CourseListFragment extends Fragment {

    public static final String EXTRA_TERM_ID = "termID";
    public static final String EXTRA_TERM_NAME = "termName";

    private String termID;
    private String termName;


    public static CourseListFragment newInstance(String termID,String termName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TERM_ID, termID);
        args.putString(EXTRA_TERM_NAME, termName);

        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            termID = getArguments().getString(EXTRA_TERM_ID, "");
            termName = getArguments().getString(EXTRA_TERM_NAME, "");
        }
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(termName);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}