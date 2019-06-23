package ghelani.kshamina.sssc_android_app.event;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class EventSingleFragment extends Fragment {

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

        Event event = (Event) getArguments().getSerializable("event");
        TextView title = view.findViewById(R.id.eventTitle);
        title.setText(event.getName());

        TextView description = view.findViewById(R.id.eventDescription);
        description.setText(event.getDescription());

        TextView rawTime = view.findViewById(R.id.rawTime);
        rawTime.setText(event.getDateDisplayString() + "\n" + event.getRawTime());

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

        if (id == R.id.actionURL) {
            // do something here
        }
        return super.onOptionsItemSelected(item);
    }

}
