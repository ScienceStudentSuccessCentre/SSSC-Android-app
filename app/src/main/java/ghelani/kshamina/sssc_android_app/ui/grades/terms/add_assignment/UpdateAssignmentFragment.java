package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.entity.AssignmentWithWeight;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseViewModel;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;

public class UpdateAssignmentFragment extends Fragment {

    private static final String UPDATE_ASSIGNMENT= "update_assignment";

    private AssignmentWithWeight assignment;

    private TermEntity term;

    private MainListAdapter adapter;

    @Inject
    ViewModelFactory viewModelFactory;

    private TextView title;

    private RecyclerView recyclerView;

    private Button updateButton;

    private Button cancelButton;

    private AddAssignmentViewModel updateAssignmentViewModel;

    public UpdateAssignmentFragment() {
        // Required empty public constructor
    }

    public static UpdateAssignmentFragment newInstance(AssignmentWithWeight assignment) {
        UpdateAssignmentFragment fragment = new UpdateAssignmentFragment();
        Bundle args = new Bundle();
        args.putSerializable(UPDATE_ASSIGNMENT, assignment);
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
            assignment = (AssignmentWithWeight) getArguments().getSerializable(UPDATE_ASSIGNMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);

        recyclerView = view.findViewById(R.id.inputRecyclerView);
        updateButton = view.findViewById(R.id.submitButton);
        title = view.findViewById(R.id.title);
        cancelButton = view.findViewById(R.id.cancelButton);

        DividerItemDecoration decoration = courseListDecoration();

        recyclerView.addItemDecoration(decoration);

        title.setText("Update Assignment");
        updateButton.setText("UPDATE");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateAssignmentViewModel = new ViewModelProvider(this, viewModelFactory).get(AddAssignmentViewModel.class);

        updateAssignmentViewModel.getInputItems().observe(this, items -> {
            recyclerView.setAdapter(new MainListAdapter(requireActivity(), items));
        });
        updateAssignmentViewModel.isSubmitEnabled().observe(this, isEnabled -> updateButton.setEnabled(isEnabled));
        updateAssignmentViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        updateAssignmentViewModel.isSubmitted().observe(this, isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });

        updateButton.setOnClickListener(v -> updateAssignmentViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        updateAssignmentViewModel.fetchAssignmentToUpdate(assignment);
    }

    private void returnToPreviousScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    private DividerItemDecoration courseListDecoration() {
        return new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL) {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Drawable mDivider = getContext().getDrawable(R.drawable.recyclerview_divider);

                int right = parent.getWidth() - parent.getPaddingRight();
                int left = parent.getPaddingLeft();
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount - 1; i++) {
                    //Remove line divider between assignment weight not and Override Calculated Grade heading
                    if (i == childCount - 4) {
                        continue;
                    }
                    View child = parent.getChildAt(i);
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mDivider.getIntrinsicHeight();

                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        };
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