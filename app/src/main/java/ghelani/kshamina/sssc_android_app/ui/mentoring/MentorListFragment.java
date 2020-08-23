package ghelani.kshamina.sssc_android_app.ui.mentoring;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.email_dialog.EmailBuilder;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

public class MentorListFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private RecyclerView mentorRecyclerView;

    private TextView emptyListMessage;

    private MentorListViewModel mentorListViewModel;

    public MentorListFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_mentor_list, container, false);
        ButterKnife.bind(this, view);
        return inflater.inflate(R.layout.fragment_mentor_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton emailFab = view.findViewById(R.id.sendEmailFab);
        MainApplication appSettings = (MainApplication) getActivity().getApplication();
        if (appSettings.isEnableEmailMentorRegistration()) {
            emailFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_email_24, null));
        } else {
            emailFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_link_white_24dp, null));
        }

        emailFab.setOnClickListener(v -> {
            if (appSettings.isEnableEmailMentorRegistration()) {
                sendMentorRegistrationEmail();
            } else {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://central.carleton.ca/"));
                startActivity(browserIntent);
            }
        });

        emptyListMessage = view.findViewById(R.id.emptyMentorListText);

        mentorRecyclerView = view.findViewById(R.id.mentorRecyclerView);
        mentorRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        mentorListViewModel = new ViewModelProvider(this, viewModelFactory).get(MentorListViewModel.class);

        mentorListViewModel.getMentors().observe(this, mentors -> {
            if (mentors.isEmpty()) {
                emptyListMessage.setVisibility(View.VISIBLE);
                mentorRecyclerView.setVisibility(View.GONE);
            } else {
                emptyListMessage.setVisibility(View.GONE);
                mentorRecyclerView.setVisibility(View.VISIBLE);
                mentorRecyclerView.setAdapter(new MainListAdapter(getActivity(), mentors));
            }
        });

        mentorListViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));

        mentorListViewModel.fetchMentors();
    }

    private void sendMentorRegistrationEmail() {
        MainApplication appSettings = (MainApplication) getActivity().getApplication();
        if (appSettings.hasStudentInformation()) {
            EmailBuilder.confirmSendEmail(getActivity(), EmailBuilder.EmailType.MENTOR_BOOKING, null);
        } else {
            EmailBuilder.showStudentNameDialog(getActivity(), EmailBuilder.EmailType.MENTOR_BOOKING, null);
        }
    }
}