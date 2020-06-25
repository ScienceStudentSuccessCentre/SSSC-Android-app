package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

public class CourseListFragment extends Fragment {

    public static final String EXTRA_TERM_ID = "termID";
    public static final String EXTRA_TERM_NAME = "termName";

    private String termID;
    private String termName;

    @Inject
    ViewModelFactory viewModelFactory;

    private CoursesViewModel courseViewModel;

    @BindView(R.id.coursesRecyclerView)
    RecyclerView courseRecyclerView;

    public static CourseListFragment newInstance(String termID,String termName) {
        Bundle args = new Bundle();
        args.putString(EXTRA_TERM_ID, termID);
        args.putString(EXTRA_TERM_NAME, termName);

        CourseListFragment fragment = new CourseListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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

        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton addCourseBtn = view.findViewById(R.id.addCourseFab);
        addCourseBtn.setOnClickListener(v -> {
            openAddCourseScreen();
        });

        courseRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        courseViewModel = new ViewModelProvider(this, viewModelFactory).get(CoursesViewModel.class);
        courseViewModel.state.observe(this, termViewState -> {
            if (termViewState.isLoading()) {
                System.out.println("Courses Loading");
            } else if (termViewState.isError()) {
                System.out.println("Course load ERROR: " + termViewState.getError());
            } else if (termViewState.isSuccess()) {

                courseRecyclerView.setAdapter(new MainListAdapter(getActivity(), courseViewModel.getCourseItems()));
                courseRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        courseViewModel.isDeleteMode.observe(this, isDeleteMode -> {

            courseViewModel.fetchCoursesByTermId(termID);

        });

        courseViewModel.fetchCoursesByTermId(termID);
    }

    private void openAddCourseScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, AddCourseFragment.newInstance(termID));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}