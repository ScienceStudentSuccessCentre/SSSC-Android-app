package ghelani.kshamina.sssc_android_app.event;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Event event = (Event) getArguments().getSerializable("event");
        TextView title = view.findViewById(R.id.eventTitle);
        title.setText(event.getEvent());

        TextView description = view.findViewById(R.id.eventDescription);
        description.setText(event.getDescription());

        return view;
    }
}
