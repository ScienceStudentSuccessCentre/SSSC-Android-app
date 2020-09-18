package ghelani.kshamina.sssc_android_app.ui.mentoring;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Mentor;
import ghelani.kshamina.sssc_android_app.ui.email_dialog.EmailBuilder;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MentorDetailFragment extends Fragment {

    private static final String ARG_MENTOR = "mentor";

    private Mentor mentor;

    public MentorDetailFragment() {
        // Required empty public constructor
    }

    public static MentorDetailFragment newInstance(Mentor mentor) {
        MentorDetailFragment fragment = new MentorDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MENTOR, mentor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mentor = (Mentor) getArguments().getSerializable(ARG_MENTOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mentor_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton emailFab = view.findViewById(R.id.mentorDetailFab);
        MainApplication appSettings = (MainApplication) getActivity().getApplication();
        if (appSettings.isEnableEmailMentorRegistration()) {
            emailFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_email_24, null));
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://central.carleton.ca/"));
            startActivity(browserIntent);
        }

        emailFab.setOnClickListener(v -> {
            if (appSettings.isEnableEmailMentorRegistration()) {
                sendMentorRegistrationEmail();
            } else {
                emailFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_link_white_24dp, null));
            }
        });

        ImageView mentorImage = view.findViewById(R.id.mentorImage);
        TextView mentorNameTextView = view.findViewById(R.id.mentorName);
        TextView mentorBioTextView = view.findViewById(R.id.mentorBio);
        TextView mentorDegreeTextView = view.findViewById(R.id.mentorDegree);
        TextView mentorTeamTextView = view.findViewById(R.id.mentorTeam);

        mentorNameTextView.setText(mentor.getName());
        mentorBioTextView.setText(Html.fromHtml(mentor.getBio()));
        mentorDegreeTextView.setText(mentor.getDegree());
        mentorTeamTextView.setText(mentor.getTeam());
        Picasso.get().load(mentor.getImageUrl()).transform(new RoundedCornersTransformation(250, 5)).into(mentorImage);
    }

    private void sendMentorRegistrationEmail() {
        MainApplication appSettings = (MainApplication) getActivity().getApplication();
        if (appSettings.hasStudentInformation()) {
            EmailBuilder.confirmSendEmail(getActivity(), EmailBuilder.EmailType.SPECIFIC_MENTOR_BOOKING, mentor);
        } else {
            EmailBuilder.showStudentNameDialog(getActivity(), EmailBuilder.EmailType.SPECIFIC_MENTOR_BOOKING, mentor);
        }
    }
}