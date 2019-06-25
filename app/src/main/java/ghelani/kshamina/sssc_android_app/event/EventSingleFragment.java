package ghelani.kshamina.sssc_android_app.event;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ghelani.kshamina.sssc_android_app.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class EventSingleFragment extends Fragment {
    private Event event;

    public EventSingleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_single, container, false);
        setHasOptionsMenu(true);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        event = (Event) getArguments().getSerializable("event");
        TextView title = view.findViewById(R.id.eventTitle);
        title.setText(event.getName());

        TextView description = view.findViewById(R.id.eventDescription);
        description.setText(event.getDescription());
//        description.setMovementMethod(new ScrollingMovementMethod());

        TextView rawTime = view.findViewById(R.id.rawTime);
        String time = event.getDateDisplayStringSingle() + "\n" + event.getRawTime();
        rawTime.setText(time);

        TextView location = view.findViewById(R.id.location);
        location.setText(event.getLocation());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.share) {
            ShareCompat.IntentBuilder.from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle("Share event link!")
                    .setText(event.getUrl().toString())
                    .startChooser();


//            Intent shareIntent = new Intent(Intent.ACTION_SEND);
//            shareIntent.setType("text/plain*");// You Can set source type here like video, image text, etc.
//            shareIntent.putExtra(Intent.EXTRA_STREAM, );
//            shareIntent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//            startActivity(Intent.createChooser(shareIntent, "Share event!"));
        }
        return super.onOptionsItemSelected(item);
    }

}
