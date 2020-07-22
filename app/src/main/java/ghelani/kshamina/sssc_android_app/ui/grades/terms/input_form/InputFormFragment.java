package ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.AddAssignmentViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_assignment.SelectWeightViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.SelectFinalGradeViewModel;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermViewModel;

public class InputFormFragment extends Fragment {

    public enum FormType {
        ADD_TERM, ADD_COURSE, ADD_ASSIGNMENT, SELECT_FINAL_GRADE, SELECT_WEIGHT, UPDATE_COURSE, UPDATE_ASSIGNMENT
    }

    private static final String ID = "id";
    private static final String TYPE = "type";

    private String id;
    private FormType type;

    InputFormViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.inputRecyclerView)
    RecyclerView inputItemRecyclerView;

    @BindView(R.id.submitButton)
    Button submitButton;

    @BindView(R.id.cancelButton)
    Button cancelButton;

    public InputFormFragment() {
        // Required empty public constructor
    }

    public static InputFormFragment newInstance(String id, String type) {
        InputFormFragment fragment = new InputFormFragment();
        Bundle args = new Bundle();
        args.putString(ID, id);
        args.putString(TYPE, type);
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
            id = getArguments().getString(ID);
            type = FormType.valueOf(getArguments().getString(TYPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        switch (type) {
            case ADD_ASSIGNMENT:
                title.setText("New Assignment");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(AddAssignmentViewModel.class);
                viewModel.createItemsList();
                break;
            case ADD_COURSE:
                title.setText("New Course");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(AddCourseViewModel.class);
                decoration = courseListDecoration();
                ((AddCourseViewModel) viewModel).setTermId(id);
                viewModel.createItemsList();
                break;
            case ADD_TERM:
                title.setText("New Term");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(AddTermViewModel.class);
                viewModel.createItemsList();
                break;
            case SELECT_FINAL_GRADE:
                title.setText("");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(SelectFinalGradeViewModel.class);
                submitButton.setVisibility(View.GONE);
                viewModel.createItemsList();
                break;
            case SELECT_WEIGHT:
                title.setText("");
                submitButton.setVisibility(View.GONE);
                viewModel = new ViewModelProvider(this, viewModelFactory).get(SelectWeightViewModel.class);
                ((SelectWeightViewModel) viewModel).setId(id);
                viewModel.createItemsList();
                break;
            case UPDATE_COURSE:
                title.setText("");
                submitButton.setText("Update");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(AddCourseViewModel.class);
                decoration = courseListDecoration();
                ((AddCourseViewModel) viewModel).fetchCourseToUpdate(id);
                break;
            case UPDATE_ASSIGNMENT:
                title.setText("");
                submitButton.setText("Update");
                viewModel = new ViewModelProvider(this, viewModelFactory).get(AddAssignmentViewModel.class);
                ((AddAssignmentViewModel) viewModel).fetchAssignmentToUpdate(id);
                break;
        }

        inputItemRecyclerView.addItemDecoration(decoration);

        viewModel.getInputItems().observe(this, items -> inputItemRecyclerView.setAdapter(new MainListAdapter(requireActivity(), items)));
        viewModel.isSubmitEnabled().observe(this, isEnabled -> submitButton.setEnabled(isEnabled));
        viewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        viewModel.isSubmitted().observe(this, isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });
        if (type.equals(FormType.SELECT_FINAL_GRADE)) {
            ((SelectFinalGradeViewModel) viewModel).getSelectedGrade().observe(this, grade -> {
                Fragment prevFragment = getFragmentManager().findFragmentByTag(String.valueOf(getFragmentManager().getBackStackEntryCount() - 2));
                AddCourseViewModel addCourseViewModel = new ViewModelProvider(prevFragment, viewModelFactory).get(AddCourseViewModel.class);
                addCourseViewModel.setFinalGrade(grade);
                returnToPreviousScreen();
            });

        }else if(type.equals(FormType.SELECT_WEIGHT)){
            ((SelectWeightViewModel) viewModel).getSelectedWeight().observe(this, weight -> {
                Fragment prevFragment = getFragmentManager().findFragmentByTag(String.valueOf(getFragmentManager().getBackStackEntryCount() - 2));
                AddAssignmentViewModel addAssignmentViewModel = new ViewModelProvider(prevFragment, viewModelFactory).get(AddAssignmentViewModel.class);
                addAssignmentViewModel.setWeight(weight);
                returnToPreviousScreen();
            });
        }

        submitButton.setOnClickListener(v -> viewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());
    }

    private void returnToPreviousScreen(){
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
        ((MainActivity) requireActivity()).getNavigatonView().setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) requireActivity()).getNavigatonView().setVisibility(View.VISIBLE);
    }
}