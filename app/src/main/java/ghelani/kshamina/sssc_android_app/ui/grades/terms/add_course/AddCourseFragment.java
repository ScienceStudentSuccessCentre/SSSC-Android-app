package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;

public class AddCourseFragment extends Fragment implements AddCourseContract.View {
    public static final String EXTRA_TERM_ID = "termID";

    @Inject
    AddCourseContract.Presenter presenter;

    @BindView(R.id.createButton)
    Button createButton;

    @BindView(R.id.cancelButton)
    Button cancelButton;

    @BindView(R.id.addCourseInputList)
    RecyclerView recyclerView;

    private String termID;

    public static AddCourseFragment newInstance(String termID) {
        AddCourseFragment fragment = new AddCourseFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TERM_ID, termID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            termID = getArguments().getString(EXTRA_TERM_ID);
        }
        presenter.setTermId(termID);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_course, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelButton.setOnClickListener(v->navigateToCoursesPage());
        createButton.setOnClickListener(v -> presenter.onCreate());
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL){
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                Drawable mDivider=getContext().getDrawable(R.drawable.recyclerview_divider);

                int right = parent.getWidth() - parent.getPaddingRight();
                int left= parent.getPaddingLeft();
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount-1; i++) {
                    //Remove line divider between assignment weight not and Override Calculated Grade heading
                    if(i == childCount-4){
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
        });

        recyclerView.setAdapter(new MainListAdapter(getActivity(), presenter.getInputItems()));
    }

    @Override
    public void navigateToCoursesPage() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    @Override
    public void setCreateEnabled(boolean value) {
        createButton.setEnabled(value);
    }
}