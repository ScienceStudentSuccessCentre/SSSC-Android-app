package ghelani.kshamina.sssc_android_app.ui.mentoring;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.email_dialog.EmailBuilder;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MentorDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGE_URL = "imageURL";
    private static final String ARG_NAME = "name";
    private static final String ARG_BIO = "bio";
    private static final String ARG_DEGREE = "degree";
    private static final String ARG_TEAM = "team";

    // TODO: Rename and change types of parameters
    private String imageURL;
    private String mentorName;
    private String mentorBio;
    private String mentorDegree;
    private String mentorTeam;

    public MentorDetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MentorDetailFragment newInstance(String url, String name, String bio, String degree, String team) {
        MentorDetailFragment fragment = new MentorDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMAGE_URL, url);
        args.putString(ARG_NAME, name);
        args.putString(ARG_BIO, bio);
        args.putString(ARG_DEGREE, degree);
        args.putString(ARG_TEAM, team);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageURL = getArguments().getString(ARG_IMAGE_URL);
            mentorName = getArguments().getString(ARG_NAME);
            mentorBio = getArguments().getString(ARG_BIO);
            mentorDegree = getArguments().getString(ARG_DEGREE);
            mentorTeam = getArguments().getString(ARG_TEAM);
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

        mentorNameTextView.setText(mentorName);
        mentorBioTextView.setText(Html.fromHtml(mentorBio));
        mentorDegreeTextView.setText(mentorDegree);
        mentorTeamTextView.setText(mentorTeam);
        Picasso.get().load(imageURL).transform(new RoundedCornersTransformation(250, 5)).into(mentorImage);
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