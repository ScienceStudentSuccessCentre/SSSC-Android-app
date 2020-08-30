package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.utils.list.SwipeToDeleteCallback;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.WeightItem;

@AndroidEntryPoint
public class AddCourseFragment extends Fragment {

    private static final String ARG_COURSE = "course";
    private static final String ARG_UPDATE = "update";

    private boolean updating;

    private CourseEntity course;

    private MainListAdapter adapter;

    private Button submitButton;

    private Button cancelButton;

    private AddCourseViewModel addCourseViewModel;

    private List<DiffItem> listItems = new ArrayList<>();

    public AddCourseFragment() {
        // Required empty public constructor
    }

    public static AddCourseFragment newInstance(CourseEntity course, boolean update) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COURSE, course);
        args.putBoolean(ARG_UPDATE, update);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            course = (CourseEntity) getArguments().getSerializable(ARG_COURSE);
            updating = getArguments().getBoolean(ARG_UPDATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_form, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.inputRecyclerView);
        submitButton = view.findViewById(R.id.submitButton);
        TextView title = view.findViewById(R.id.title);
        cancelButton = view.findViewById(R.id.cancelButton);

        DividerItemDecoration decoration = courseListDecoration();

        recyclerView.addItemDecoration(decoration);

        adapter = new MainListAdapter(requireActivity(), listItems);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper swipeHelper = new ItemTouchHelper(
                new SwipeToDeleteCallback(getContext(), index -> addCourseViewModel.removeWeight(index)));
        swipeHelper.attachToRecyclerView(recyclerView);

        title.setText(updating ? "Update Course" : "New Course");
        submitButton.setText(updating ? "UPDATE" : "CREATE");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCourseViewModel = new ViewModelProvider(this).get(AddCourseViewModel.class);

        addCourseViewModel.setTermId(course.courseTermId);

        addCourseViewModel.getInputItems().observe(getViewLifecycleOwner(), items -> {

            listItems.clear();
            listItems.addAll(items);
            adapter.notifyDataSetChanged();

        });

        addCourseViewModel.getRemoveWeightItem().observe(getViewLifecycleOwner(), index -> {
            listItems.remove(index.intValue());
            adapter.notifyItemRemoved(index);
        });

        addCourseViewModel.getAddWeightItem().observe(getViewLifecycleOwner(), diffItem -> {
            int position = ((WeightItem) diffItem).getIndex() + 6;
            listItems.add(position, diffItem);
            adapter.notifyItemInserted(position);
        });

        addCourseViewModel.getShowDialog().observe(getViewLifecycleOwner(), diffItem -> showDialog());

        addCourseViewModel.isSubmitEnabled().observe(getViewLifecycleOwner(), isEnabled -> submitButton.setEnabled(isEnabled));
        addCourseViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));
        addCourseViewModel.isSubmitted().observe(getViewLifecycleOwner(), isComplete -> {
            if (isComplete) {
                returnToPreviousScreen();
            }
        });

        submitButton.setOnClickListener(v -> addCourseViewModel.onSubmit());

        cancelButton.setOnClickListener(v -> returnToPreviousScreen());

        if (updating) {
            addCourseViewModel.fetchCourseToUpdate(course);
        } else {

            addCourseViewModel.createItemsList();
        }
    }

    private void returnToPreviousScreen() {
        FragmentManager fragmentManager = getParentFragmentManager();
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

    private void showDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Cannot Modify Weight!")
                .setMessage("Please modify or delete all assignments marked with the weight you are trying to delete.")
                .setPositiveButton("Dismiss", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
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