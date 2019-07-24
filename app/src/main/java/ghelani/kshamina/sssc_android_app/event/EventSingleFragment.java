package ghelani.kshamina.sssc_android_app.event;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.ShareCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class EventSingleFragment extends Fragment {
    private Event event;
    private NotificationCompat.Builder builder;

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
        description.setText(Html.fromHtml(event.getDescription()));

        TextView rawTime = view.findViewById(R.id.rawTime);
        String time = event.getDateDisplayStringSingle() + "\n" + event.getRawTime();
        rawTime.setText(time);

        TextView location = view.findViewById(R.id.location);
        location.setText(event.getLocation());

        builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("wowo")
                .setContentText("cool");

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
        }
        else if (id == R.id.actionURL) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(event.getActionUrl()));
            startActivity(browserIntent);
        }
        else if (id == R.id.notification) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(001, builder.build());
        }
        return super.onOptionsItemSelected(item);
    }

}
