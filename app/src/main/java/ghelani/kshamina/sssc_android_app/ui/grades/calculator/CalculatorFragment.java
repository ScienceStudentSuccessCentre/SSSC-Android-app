package ghelani.kshamina.sssc_android_app.ui.grades.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.database.CourseDao;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import ghelani.kshamina.sssc_android_app.ui.SettingsFragment;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.assignments.AssignmentListFragment;

public class CalculatorFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    // Data model
    private FilteredCourseList filteredCourseList;
    private List<CourseEntity> adapterList;    // Need to keep a reference to the list used by the adapter,

    @Inject
    GradesDatabase gradesDatabase;                                     // and add to/remove from it as needed

    // Components
    private TextView calculatedOverallCGPA;
    private TextView calculatedMajorCGPA;

    private RecyclerView recyclerView;
    private CoursesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // OnClick Listener for a single CourseEntity
    private View.OnClickListener onItemClickListener = view -> {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
        int position = viewHolder.getAdapterPosition();
        CourseEntity courseEntity = filteredCourseList.get(position);
        openCourseSingle(courseEntity, view);
    };

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup calculatorView = (ViewGroup) inflater.inflate(R.layout.fragment_calculator, container, false);

        // Initialize data model
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean includeInProgressCourses = preferences.getBoolean(SettingsFragment.KEY_PREF_INCLUDE_IN_PROGRESS_COURSES, true);
        this.filteredCourseList = new FilteredCourseList(new ArrayList<>(), includeInProgressCourses);

        this.adapterList = new ArrayList<>();

        // Setup RecyclerView
        recyclerView = calculatorView.findViewById(R.id.coursesList);
        recyclerView.setHasFixedSize(true);

        adapter = new CoursesAdapter(adapterList, getActivity(),gradesDatabase);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        // Initialize components
        calculatedOverallCGPA = calculatorView.findViewById(R.id.calculatorCalculatedOverallCGPA);
        calculatedMajorCGPA = calculatorView.findViewById(R.id.calculatorCalculatedMajorCGPA);

        // Register this Fragment as OnSharedPreferenceChangeListener
        preferences.registerOnSharedPreferenceChangeListener(this);

        // Load courses from DB
        loadCourseData();

        // Update CGPAs based on courses
        updateCGPAs();

        return calculatorView;
    }

    /**
     * Populates courseList with the contents of the database and updates the RecyclerView.
     */

    private void loadCourseData() {
        Thread thread = new Thread(() -> {
            filteredCourseList.clear();
            CourseDao courseDao = gradesDatabase.getCourseDao();
            filteredCourseList.addAll(courseDao.getAllCourses());
            updateAdapterList();
        });

        thread.start();

        try {
            thread.join();  // Wait for it to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateCGPAs() {
        double overallCGPA = Grading.calculateOverallCGPA(filteredCourseList.getCurrentCourseList());
        calculatedOverallCGPA.setText(overallCGPA == -1 ?
                "Overall CGPA: N/A" : String.format(Locale.CANADA, "Overall CGPA: %.1f", overallCGPA));

        List<CourseEntity> majorCourses = filteredCourseList.getCurrentCourseList().stream()
                .filter(course -> course.courseIsMajorCourse)
                .collect(Collectors.toList());

        double overallMajorCGPA = Grading.calculateOverallCGPA(majorCourses);
        calculatedMajorCGPA.setText(overallMajorCGPA == -1 ?
                "Major CGPA: N/A" : String.format(Locale.CANADA, "Major CGPA: %.1f", overallMajorCGPA));
    }

    private void openCourseSingle(CourseEntity courseEntity, View view) {
        // TODO implement
            ((MainActivity) requireActivity()).replaceFragment(AssignmentListFragment.newInstance(courseEntity.courseId));
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(SettingsFragment.KEY_PREF_INCLUDE_IN_PROGRESS_COURSES)) {
            boolean isIncluded = sharedPreferences.getBoolean(key, true);
            filteredCourseList.setShowInProgressCourses(isIncluded);
            updateAdapterList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Calculator");
        setHasOptionsMenu(true);
        loadCourseData();
    }

    @Override
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void updateAdapterList() {
        adapterList.clear();
        adapterList.addAll(filteredCourseList.getCurrentCourseList());

        if (getActivity() == null) return;
        getActivity().runOnUiThread(adapter::notifyDataSetChanged);
    }

    /**
     * A List, except it can switch between filtered and non-filtered mode
     */
    private class FilteredCourseList {
        private List<CourseEntity> allCourses = new ArrayList<>();
        private List<CourseEntity> filteredCourses = new ArrayList<>();

        private boolean isFiltered;

        public FilteredCourseList(List<CourseEntity> allCourses, boolean shouldShow) {
            this.addAll(allCourses);
            this.isFiltered = !shouldShow;
        }

        public CourseEntity get(int index) {
            if (isFiltered) return filteredCourses.get(index);
            return allCourses.get(index);
        }

        public int size() {
            if (isFiltered) return filteredCourses.size();
            return allCourses.size();
        }

        public void setShowInProgressCourses(boolean shouldShow) {
            this.isFiltered = !shouldShow;
        }

        public List<CourseEntity> getCurrentCourseList() {
            if (isFiltered) return this.filteredCourses;
            return this.allCourses;
        }

        public void clear() {
            allCourses.clear();
            filteredCourses.clear();
        }

        public void addAll(Collection<? extends CourseEntity> courses) {
            allCourses.addAll(courses);
            filteredCourses.addAll(
                    courses.stream()
                            .filter(course -> course.courseFinalGrade != null && !course.courseFinalGrade.isEmpty())
                            .collect(Collectors.toList())
            );
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}
