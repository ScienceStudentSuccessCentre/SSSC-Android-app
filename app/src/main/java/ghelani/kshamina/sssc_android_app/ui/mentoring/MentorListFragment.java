package ghelani.kshamina.sssc_android_app.ui.mentoring;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

public class MentorListFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private RecyclerView mentorRecyclerView;

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
        emailFab.setOnClickListener(v->sendBookingEmail());

        mentorRecyclerView = view.findViewById(R.id.mentorRecyclerView);
        mentorRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        mentorListViewModel = new ViewModelProvider(this, viewModelFactory).get(MentorListViewModel.class);

        mentorListViewModel.getMentors().observe(this, mentors -> mentorRecyclerView.setAdapter(new MainListAdapter(getActivity(), mentors)));

        mentorListViewModel.getNavigationEvent().observe(this, newFragment -> ((MainActivity) requireActivity()).replaceFragment(newFragment));

        mentorListViewModel.fetchMentors();
    }

    private void sendBookingEmail(){
        String[] recipients = {"sssc@carleton.ca"};
        String subject = "SSSC Mentor Appointment";
        String message = "Hello,\n\nI would like to book an appointment with a mentor at the SSSC!";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,recipients);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent,"Choose an email account"));
    }
}