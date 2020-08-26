package ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Assignment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.UpdateCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.required_final_grade.RequiredFinalGradeFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.utils.list.SwipeToDeleteCallback;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

@AndroidEntryPoint
public class AssignmentListFragment extends Fragment {

    private static final String COURSE_ID = "courseID";

    private String courseID;

    private AssignmentViewModel assignmentViewModel;

    @BindView(R.id.assignmentsRecyclerView)
    RecyclerView assignmentRecyclerView;

    @BindView(R.id.addAssignmentFab)
    FloatingActionButton addAssignmentFab;

    @BindView(R.id.assignmentListToolbar)
    Toolbar toolbar;

    @BindView(R.id.courseGrade)
    TextView courseGradeText;

    @BindView(R.id.calculateDesiredGrade)
    TextView calculateDesiredGrade;

    @BindView(R.id.emptyAssignmentListText)
    TextView emptyAssignmentListMessage;

    public AssignmentListFragment() {
        // Required empty public constructor
    }

    public static AssignmentListFragment newInstance(String courseID) {
        AssignmentListFragment fragment = new AssignmentListFragment();
        Bundle args = new Bundle();
        args.putString(COURSE_ID, courseID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.courseID = getArguments().getString(COURSE_ID);
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

        assignmentRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        assignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);

        MainListAdapter adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        ItemTouchHelper swipeHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getContext(), adapter, assignmentRecyclerView, (index -> assignmentViewModel.deleteAssignment(index))));
        swipeHelper.attachToRecyclerView(assignmentRecyclerView);
        assignmentRecyclerView.setAdapter(adapter);

        assignmentViewModel.getState().observe(this, assignmentViewState -> {
            if (assignmentViewState.isLoading()) {
                System.out.println("Courses Loading");
            } else if (assignmentViewState.isError()) {
                System.out.println("Course load ERROR: " + assignmentViewState.getError());
            } else if (assignmentViewState.isSuccess()) {
                toolbar.setTitle(assignmentViewModel.getCourse().courseCode);
                toolbar.setSubtitle(assignmentViewModel.getCourse().courseName);
                if (assignmentViewState.getItems().isEmpty()) {
                    emptyAssignmentListMessage.setVisibility(View.VISIBLE);
                    assignmentRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyAssignmentListMessage.setVisibility(View.GONE);
                    assignmentRecyclerView.setVisibility(View.VISIBLE);
                    adapter.setItems(assignmentViewState.getItems());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        assignmentViewModel.getCourseGrade().observe(this, grade -> {
            if (!assignmentViewModel.getCourse().courseFinalGrade.isEmpty()) {
                calculateDesiredGrade.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = courseGradeText.getLayoutParams();
                params.height = MATCH_PARENT;
                courseGradeText.setLayoutParams(params);
            } else {
                ViewGroup.LayoutParams params = courseGradeText.getLayoutParams();
                params.height = WRAP_CONTENT;
                courseGradeText.setLayoutParams(params);
                calculateDesiredGrade.setVisibility(View.VISIBLE);
            }
            courseGradeText.setText("Overall Grade: " + grade);
        });

        assignmentViewModel.getNavigationEvent().observe(this, this::replaceFragment);

        calculateDesiredGrade.setOnClickListener(v -> {
            replaceFragment(RequiredFinalGradeFragment.newInstance(assignmentViewModel.getCourseData()));
        });//replaceFragment(InputFormFragment.newInstance(courseID, InputFormFragment.FormType.REQUIRED_FINAL_GRADE.toString())));

        addAssignmentFab.setOnClickListener(v -> {
            if (assignmentViewModel.assignmentWeightsAvailable()) {
                Assignment newAssignment = new Assignment("", -1, 0, "", courseID);
                replaceFragment(AddAssignmentFragment.newInstance(newAssignment));
                ///replaceFragment(InputFormFragment.newInstance(courseID , InputFormFragment.FormType.ADD_ASSIGNMENT.toString()));
            } else {
                showNoWeightsDialog();
            }
        });

        assignmentViewModel.fetchCourseAssignments(courseID);
    }

    private void showNoWeightsDialog() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Unable to create assignment!")
                .setMessage("You must add a weight to your course before you can create an assignment.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
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
            assignmentViewModel.fetchCourseAssignments(courseID);

            return true;
        } else if (item.getItemId() == R.id.editCourseAction) {
           //replaceFragment(InputFormFragment.newInstance(courseID, InputFormFragment.FormType.UPDATE_COURSE.toString()));
           replaceFragment(UpdateCourseFragment.newInstance(assignmentViewModel.getCourse()));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        assignmentViewModel.fetchCourseAssignments(courseID);
    }
}