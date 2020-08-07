package ghelani.kshamina.sssc_android_app.ui.mentoring;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import ghelani.kshamina.sssc_android_app.R;
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

        ImageView mentorImage = view.findViewById(R.id.mentorImage);
        TextView mentorNameTextView = view.findViewById(R.id.mentorName);
        TextView mentorBioTextView = view.findViewById(R.id.mentorBio);
        TextView mentorDegreeTextView = view.findViewById(R.id.mentorDegree);
        TextView mentorTeamTextView = view.findViewById(R.id.mentorTeam);

        mentorNameTextView.setText(mentorName);
        mentorBioTextView.setText(mentorBio);
        mentorDegreeTextView.setText(mentorDegree);
        mentorTeamTextView.setText(mentorTeam);
        Picasso.get().load(imageURL).transform(new RoundedCornersTransformation(250,5)).into(mentorImage);
    }
}