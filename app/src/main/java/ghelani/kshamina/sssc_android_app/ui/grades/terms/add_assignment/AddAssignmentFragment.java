package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment;

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
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

@AndroidEntryPoint
public class AddAssignmentFragment extends Fragment {

    private static final String ASSIGNMENT_OBJECT = "assignment";

    private Assignment assignment;

    private MainListAdapter adapter;

    private TextView title;

    private RecyclerView recyclerView;

    private Button submitButton;

    private Button cancelButton;

    private AddAssignmentViewModel addAssignmentViewModel;

    public AddAssignmentFragment() {
        // Required empty public constructor
    }

    public static AddAssignmentFragment newInstance(Assignment assignment) {
        AddAssignmentFragment fragment = new AddAssignmentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ASSIGNMENT_OBJECT, assignment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assignment = (Assignment) getArguments().getSerializable(ASSIGNMENT_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);

        recyclerView = view.findViewById(R.id.inputRecyclerView);
        submitButton = view.findViewById(R.id.submitButton);
        title = view.findViewById(R.id.title);
        cancelButton = view.findViewById(R.id.cancelButton);

        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(decoration);

        adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(adapter);

        title.setText("New Assignment");

        submitButton.setOnClickListener(v -> addAssignmentViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addAssignmentViewModel = new ViewModelProvider(this).get(AddAssignmentViewModel.class);

        addAssignmentViewModel.getInputItems().observe(getViewLifecycleOwner(), items -> {
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        });
        addAssignmentViewModel.isSubmitEnabled().observe(getViewLifecycleOwner(), isEnabled -> submitButton.setEnabled(isEnabled));
        addAssignmentViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        addAssignmentViewModel.isSubmitted().observe(getViewLifecycleOwner(), isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });

        addAssignmentViewModel.setNewAssignment(assignment);
        addAssignmentViewModel.createItemsList();
    }

    private void returnToPreviousScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    public AddAssignmentViewModel getViewModel() {
        return addAssignmentViewModel;
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