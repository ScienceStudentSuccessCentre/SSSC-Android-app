package ghelani.kshamina.sssc_android_app.ui.grades.terms.select_weight;

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
import ghelani.kshamina.sssc_android_app.entity.Weight;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.SelectItemViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

public class SelectWeightFragment extends Fragment {

    private static final String ARG_VIEW_MODEL = "viewmodel";
    private static final String ARG_COURSE_ID = "courseId";

    private String courseId;

    private SelectItemViewModel<Weight> selectItemViewModel;

    private MainListAdapter adapter;

    @Inject
    ViewModelFactory viewModelFactory;

    private TextView title;

    private RecyclerView recyclerView;

    private Button submitButton;

    private Button cancelButton;

    private SelectWeightViewModel selectWeightViewModel;

    public SelectWeightFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NotNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    public static SelectWeightFragment newInstance(SelectItemViewModel<Weight> viewModel, String courseId) {
        SelectWeightFragment fragment = new SelectWeightFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VIEW_MODEL, viewModel);
        args.putString(ARG_COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectItemViewModel = (SelectItemViewModel<Weight>) getArguments().getSerializable(ARG_VIEW_MODEL);
            courseId = getArguments().getString(ARG_COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);

        recyclerView = view.findViewById(R.id.inputRecyclerView);
        submitButton = view.findViewById(R.id.submitButton);
        title = view.findViewById(R.id.title);
        cancelButton = view.findViewById(R.id.cancelButton);

        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);

        adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(adapter);

        title.setText("Select a Weight");

        submitButton.setOnClickListener(v -> selectWeightViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitButton.setVisibility(View.GONE);
        selectWeightViewModel = new ViewModelProvider(this, viewModelFactory).get(SelectWeightViewModel.class);
        selectWeightViewModel.setId(courseId);
        selectWeightViewModel.createItemsList();

        selectWeightViewModel.getInputItems().observe(this, items -> recyclerView.setAdapter(new MainListAdapter(requireActivity(), items)));
        selectWeightViewModel.isSubmitEnabled().observe(this, isEnabled -> submitButton.setEnabled(isEnabled));
        selectWeightViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        selectWeightViewModel.isSubmitted().observe(this, isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });
        selectWeightViewModel.getSelectedWeight().observe(this, weight -> {
            selectItemViewModel.setSelectedItem(weight);
            returnToPreviousScreen();
        });

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());
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