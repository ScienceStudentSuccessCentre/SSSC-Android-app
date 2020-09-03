package ghelani.kshamina.sssc_android_app.ui.grades.terms.required_final_grade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

@AndroidEntryPoint
public class RequiredFinalGradeFragment extends Fragment {

    private static final String ARG_COURSE = "course";

    private CourseWithAssignmentsAndWeights course;

    private MainListAdapter adapter;

    private Button doneButton;

    private RequiredFinalGradeViewModel requiredFinalGradeViewModel;

    private RecyclerView recyclerView;

    public RequiredFinalGradeFragment() {
        // Required empty public constructor
    }

    public static RequiredFinalGradeFragment newInstance(CourseWithAssignmentsAndWeights course) {
        RequiredFinalGradeFragment fragment = new RequiredFinalGradeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (CourseWithAssignmentsAndWeights) getArguments().getSerializable(ARG_COURSE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);
        recyclerView = view.findViewById(R.id.inputRecyclerView);
        doneButton = view.findViewById(R.id.submitButton);
        doneButton.setVisibility(View.GONE);
        TextView title = view.findViewById(R.id.title);
        Button cancelButton = view.findViewById(R.id.cancelButton);
        cancelButton.setText("Back");

        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);

        adapter = new MainListAdapter(getActivity(), Collections.emptyList());


        title.setText("");

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requiredFinalGradeViewModel = new ViewModelProvider(this).get(RequiredFinalGradeViewModel.class);
        requiredFinalGradeViewModel.setCourse(course);
        requiredFinalGradeViewModel.getInputItems().observe(getViewLifecycleOwner(), items -> {
            recyclerView.setAdapter(new MainListAdapter(getActivity(), items));
        });
        requiredFinalGradeViewModel.isSubmitEnabled().observe(getViewLifecycleOwner(), isEnabled -> doneButton.setEnabled(isEnabled));
        requiredFinalGradeViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        requiredFinalGradeViewModel.isSubmitted().observe(getViewLifecycleOwner(), isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });

        requiredFinalGradeViewModel.createItemsList();
    }

    private void returnToPreviousScreen() {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).getNavigationView().setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) requireActivity()).getNavigationView().setVisibility(View.VISIBLE);
    }
}