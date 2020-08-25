package ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course.AddCourseFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.list.SwipeToDeleteCallback;

public class CourseListFragment extends Fragment {

    public static final String EXTRA_TERM = "term";

    private TermEntity term;

    @Inject
    ViewModelFactory viewModelFactory;

    private CoursesViewModel courseViewModel;

    @BindView(R.id.coursesRecyclerView)
    RecyclerView courseRecyclerView;

    @BindView(R.id.termCredits)
    TextView creditsText;

    @BindView(R.id.termGPAText)
    TextView gpaText;

    @BindView(R.id.courseListToolbar)
    Toolbar toolbar;

    @BindView(R.id.emptyCourseListText)
    TextView emptyCourseListMessage;

    public static CourseListFragment newInstance(TermEntity term) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TERM, term);
        CourseListFragment fragment = new CourseListFragment();
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

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            term = (TermEntity) getArguments().getSerializable(EXTRA_TERM);
        }
        View view = inflater.inflate(R.layout.fragment_course_list, container, false);
        ButterKnife.bind(this, view);
        toolbar.setTitle(term.toString());
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton addCourseBtn = view.findViewById(R.id.addCourseFab);
        addCourseBtn.setOnClickListener(v -> replaceFragment(AddCourseFragment.newInstance(term)));


        MainListAdapter adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        ItemTouchHelper swipeHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getContext(), adapter, courseRecyclerView, (index) -> courseViewModel.deleteItem(index)));
        swipeHelper.attachToRecyclerView(courseRecyclerView);
        courseRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        courseRecyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this, viewModelFactory).get(CoursesViewModel.class);
        courseViewModel.state.observe(this, courseListViewState -> {
            if (courseListViewState.isLoading()) {
                System.out.println("Courses Loading");
            } else if (courseListViewState.isError()) {
                System.out.println("Course load ERROR: " + courseListViewState.getError());
            } else if (courseListViewState.isSuccess()) {

                if (courseListViewState.getItems().isEmpty()) {
                    emptyCourseListMessage.setVisibility(View.VISIBLE);
                    courseRecyclerView.setVisibility(View.GONE);
                } else {
                    emptyCourseListMessage.setVisibility(View.GONE);
                    courseRecyclerView.setVisibility(View.VISIBLE);
                    adapter.setItems(courseListViewState.getItems());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        courseViewModel.navigationEvent.observe(this, newFragment -> replaceFragment(newFragment));
        courseViewModel.creditsState.observe(this, credits -> creditsText.setText("Credits: " + credits));
        courseViewModel.termGPA.observe(this, gpa -> gpaText.setText("Term GPA: " + (gpa == -1 ? "N/A" : gpa)));
        courseViewModel.fetchCoursesByTermId(term.termId);
    }

    private void replaceFragment(Fragment newFragment) {
        ((MainActivity) requireActivity()).replaceFragment(newFragment);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.course_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.deleteActionItem) {
            if (courseViewModel.isDeleteMode) {
                item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete));
                courseViewModel.setIsDeleteMode(false);
            } else {
                item.setIcon(R.drawable.ic_close);
                courseViewModel.setIsDeleteMode(true);
            }
            courseViewModel.fetchCoursesByTermId(term.termId);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}