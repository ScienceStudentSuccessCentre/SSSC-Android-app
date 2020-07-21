package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.InputItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;

public class AssignmentListFragment extends Fragment {

    private static final String COURSE_ID = "courseID";
    private static final String COURSE_NAME = "courseName";
    private static final String COURSE_CODE = "courseCode";

    private String courseId;
    private String courseName;
    private String courseCode;

    private AssignmentViewModel assignmentViewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.assignmentsRecyclerView)
    RecyclerView assignmentRecyclerView;

    @BindView(R.id.addAssignmentFab)
    FloatingActionButton addAssignmentFab;

    @BindView(R.id.assignmentListToolbar)
    Toolbar toolbar;

    public AssignmentListFragment() {
        // Required empty public constructor
    }

    public static AssignmentListFragment newInstance(String courseId, String courseName, String courseCode) {
        AssignmentListFragment fragment = new AssignmentListFragment();
        Bundle args = new Bundle();
        args.putString(COURSE_ID, courseId);
        args.putString(COURSE_NAME, courseName);
        args.putString(COURSE_CODE, courseCode);
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
            courseId = getArguments().getString(COURSE_ID);
            courseCode = getArguments().getString(COURSE_CODE);
            courseName = getArguments().getString(COURSE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment_list, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar.setTitle(courseCode);
        toolbar.setSubtitle(courseName);

        addAssignmentFab.setOnClickListener(v -> replaceFragment(InputFormFragment.newInstance(courseId, InputFormFragment.FormType.ADD_ASSIGNMENT.toString())));

        assignmentRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        assignmentViewModel = new ViewModelProvider(this, viewModelFactory).get(AssignmentViewModel.class);

        assignmentViewModel.getState().observe(this, assignmentViewState -> {
            if (assignmentViewState.isLoading()) {
                System.out.println("Courses Loading");
            } else if (assignmentViewState.isError()) {
                System.out.println("Course load ERROR: " + assignmentViewState.getError());
            } else if (assignmentViewState.isSuccess()) {
                List<DiffItem> displayableItems = new ArrayList<>();
                for (ListItem item : assignmentViewState.getItems()) {
                    displayableItems.add(item);
                }
                assignmentRecyclerView.setAdapter(new MainListAdapter(getActivity(), displayableItems));
            }
        });

        assignmentViewModel.getNavigationEvent().observe(this, this::replaceFragment);

        assignmentViewModel.fetchCourseAssignments(courseId);
    }

    private void replaceFragment(Fragment newFragment) {
        ((MainActivity) requireActivity()).replaceFragment(newFragment);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.assignment_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.deleteActionItem) {
            if (assignmentViewModel.isDeleteMode()) {
                item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete));
                assignmentViewModel.setDeleteMode(false);
            } else {
                item.setIcon(R.drawable.ic_close);
                assignmentViewModel.setDeleteMode(true);
            }
            assignmentViewModel.fetchCourseAssignments(courseId);

            return true;
        } else if (item.getItemId() == R.id.editCourseAction) {
            replaceFragment(InputFormFragment.newInstance(courseId, InputFormFragment.FormType.UPDATE_COURSE.toString()));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        assignmentViewModel.fetchCourseAssignments(courseId);
    }
}