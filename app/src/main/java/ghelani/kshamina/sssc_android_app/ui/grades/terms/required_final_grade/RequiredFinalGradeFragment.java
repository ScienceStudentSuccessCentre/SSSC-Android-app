package ghelani.kshamina.sssc_android_app.ui.grades.terms.required_final_grade;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.entity.CourseWithAssignmentsAndWeights;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

public class RequiredFinalGradeFragment extends Fragment {

    private static final String ARG_COURSE = "course";

    private CourseWithAssignmentsAndWeights course;

    private MainListAdapter adapter;

    @Inject
    ViewModelFactory viewModelFactory;

    private TextView title;

    private RecyclerView recyclerView;

    private Button doneButton;

    private Button cancelButton;

    private RequiredFinalGradeViewModel requiredFinalGradeViewModel;

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
    public void onAttach(@NotNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
        title = view.findViewById(R.id.title);
        cancelButton = view.findViewById(R.id.cancelButton);

        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);

        adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(adapter);

        title.setText("");

        doneButton.setOnClickListener(v -> requiredFinalGradeViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requiredFinalGradeViewModel = new ViewModelProvider(this, viewModelFactory).get(RequiredFinalGradeViewModel.class);
        requiredFinalGradeViewModel.setCourse(course);
        requiredFinalGradeViewModel.getInputItems().observe(this, items -> {
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        });
        requiredFinalGradeViewModel.isSubmitEnabled().observe(this, isEnabled -> doneButton.setEnabled(isEnabled));
        requiredFinalGradeViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        requiredFinalGradeViewModel.isSubmitted().observe(this, isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });

        requiredFinalGradeViewModel.createItemsList();
    }

    private void returnToPreviousScreen() {
        FragmentManager fragmentManager = getFragmentManager();
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