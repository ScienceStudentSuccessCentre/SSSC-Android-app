package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ghelani.kshamina.sssc_android_app.BaseDaggerFragment;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsFragment;

public class AddTermFragment extends BaseDaggerFragment implements AddTermContract.View {

    @Inject
    AddTermContract.Presenter presenter;

    @BindView(R.id.seasonRecyclerView)
    RecyclerView seasonListView;

    @BindView(R.id.yearRecyclerView)
    RecyclerView yearListView;

    @BindView(R.id.createButton)
    Button createButton;

    @BindView(R.id.cancelButton)
    Button cancelButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_add_term, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createButton.setOnClickListener(v -> {
            presenter.onCreate();
        });

        cancelButton.setOnClickListener(v -> {
            presenter.onCancel();
        });

        seasonListView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        yearListView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        presenter.getOptions();

    }

    @Override
    public void navigateToTermsPage() {
        ((MainActivity)requireActivity()).changeFragment(new GradesFragment());
    }

    @Override
    public void setCreateButtonEnabled(boolean isEnabled) {
        createButton.setEnabled(isEnabled);
    }

    @Override
    public void displaySeasons(List<String> options, int selected) {
        seasonListView.setAdapter(new CheckListAdapter(options, selected, AddTermContract.Presenter.SEASON, presenter));
    }

    @Override
    public void displayYears(List<String> options, int selected) {
        yearListView.setAdapter(new CheckListAdapter(options, selected, AddTermContract.Presenter.YEAR, presenter));
    }

    @Override
    public void onResume() {
        super.onResume();
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onPause() {
        super.onPause();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
    }
}