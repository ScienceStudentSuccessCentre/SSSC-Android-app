package ghelani.kshamina.sssc_android_app.ui.grades.terms.select_grade;

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

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.SelectItemViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

@AndroidEntryPoint
public class SelectGradeFragment extends Fragment {

    private static final String ARG_VIEW_MODEL = "viewmodel";

    private SelectItemViewModel<String> selectItemViewModel;

    private MainListAdapter adapter;

    private TextView title;

    private RecyclerView recyclerView;

    private Button submitButton;

    private Button cancelButton;

    private SelectFinalGradeViewModel selectFinalGradeViewModel;

    public SelectGradeFragment() {
        // Required empty public constructor
    }

    public static SelectGradeFragment newInstance(SelectItemViewModel<String> viewModel) {
        SelectGradeFragment fragment = new SelectGradeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_VIEW_MODEL, viewModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectItemViewModel = (SelectItemViewModel) getArguments().getSerializable(ARG_VIEW_MODEL);
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

        title.setText("Select a Grade");

        submitButton.setOnClickListener(v -> selectFinalGradeViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitButton.setVisibility(View.GONE);
        selectFinalGradeViewModel = new ViewModelProvider(this).get(SelectFinalGradeViewModel.class);
        selectFinalGradeViewModel.createItemsList();

        selectFinalGradeViewModel.getInputItems().observe(this, items -> recyclerView.setAdapter(new MainListAdapter(requireActivity(), items)));
        selectFinalGradeViewModel.isSubmitEnabled().observe(this, isEnabled -> submitButton.setEnabled(isEnabled));
        selectFinalGradeViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        selectFinalGradeViewModel.isSubmitted().observe(this, isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });
        selectFinalGradeViewModel.getSelectedGrade().observe(this, grade -> {
            selectItemViewModel.setSelectedItem(grade);
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