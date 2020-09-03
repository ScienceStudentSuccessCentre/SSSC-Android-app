package ghelani.kshamina.sssc_android_app.ui.event;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.ShareCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.MainApplication;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.ui.email_dialog.EmailBuilder;

public class EventSingleFragment extends Fragment {

    private static final String EVENT_ARG = "event";

    private Event event;
    private ImageView imageView;
    private boolean eventNotification;
    SharedPreferences preferences;

    public EventSingleFragment() {
        // Required empty public constructor
    }

    public static EventSingleFragment newInstance(Event event) {
        EventSingleFragment fragment = new EventSingleFragment();
        Bundle args = new Bundle();
        args.putSerializable(EVENT_ARG, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.event = (Event) getArguments().getSerializable(EVENT_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_single, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.eventDetailToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (getArguments() != null) {
            event = (Event) getArguments().getSerializable("event");
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        eventNotification = preferences.getBoolean(event.getId(), false);
        setupNotification(getNotification());

        TextView title = view.findViewById(R.id.eventTitle);
        title.setText(event.getName());

        TextView description = view.findViewById(R.id.eventDescription);
        description.setText(Html.fromHtml(event.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        description.setMovementMethod(LinkMovementMethod.getInstance());

        imageView = view.findViewById(R.id.eventImage);
        if (event.getImageURL() != null) this.loadEventImage();

        TextView rawTime = view.findViewById(R.id.rawTime);
        String time = event.getDateDisplayStringSingle() + "\n" + event.getRawTime();
        rawTime.setText(time);

        TextView location = view.findViewById(R.id.location);
        location.setText(event.getLocation());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        menu.setGroupVisible(R.id.event_single_menu, true);
    }

    @Override
    public void onPrepareOptionsMenu(@NotNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem notification = menu.findItem(R.id.notification);
        MenuItem bookEvent = menu.findItem(R.id.actionURL);

        if (eventNotification) {
            notification.setIcon(R.drawable.ic_notifications_active_black_24dp);
        } else {
            notification.setIcon(R.drawable.ic_notifications_none_white_24dp);
        }

        MainApplication appSettings = (MainApplication) requireActivity().getApplication();
        if (appSettings.isEnableEmailEventRegistration() && event.getActionUrl().contains("https://central.carleton.ca")) {
            bookEvent.setIcon(R.drawable.ic_email_24);
        } else {
            bookEvent.setIcon(R.drawable.ic_link_white_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                ShareCompat.IntentBuilder.from(requireActivity())
                        .setType("text/plain")
                        .setChooserTitle("Share event link!")
                        .setText(event.getUrl())
                        .startChooser();
                break;

            case R.id.actionURL:
                if (((MainApplication) requireActivity().getApplication()).isEnableEmailEventRegistration() && event.getActionUrl().contains("https://central.carleton.ca/")) {
                    sendEventRegistrationEmail();
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(event.getActionUrl()));
                    startActivity(browserIntent);
                }
                break;

            case R.id.notification:
                toggleNotificationValue();
                setupNotification(getNotification());
                break;

            default:
                return false;

        }
        return super.onOptionsItemSelected(item);

    }

    public void setupNotification(Notification notification) {
        Intent notificationIntent = new Intent(getActivity(), EventAlert.class);
        notificationIntent.putExtra("notification", notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 34232, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);

        if (eventNotification) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, event.getNotificationTime(), pendingIntent);

//          Uncomment to send alert right away
          //  alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 3000, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void toggleNotificationValue() {
        SharedPreferences.Editor editor = this.preferences.edit();
        if (this.eventNotification) {
            editor.putBoolean(event.getId(), false); // value to store
            editor.commit();
            this.eventNotification = false;

        } else {
            editor.putBoolean(event.getId(), true); // value to store
            editor.commit();
            this.eventNotification = true;

            new AlertDialog.Builder(getContext())
                    .setMessage("You'll be sent a notification an hour before this event starts.")
                    .show();
        }
        requireActivity().invalidateOptionsMenu();
    }

    private Notification getNotification() {
        String title = event.getName();
        String description = "Today at " + event.getRawTime() + ", " + event.getLocation();

        Intent resultIntent = new Intent(getContext(), MainActivity.class);
        resultIntent.putExtra("event", event);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(requireContext());
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), "channel_id")
                .setSmallIcon(R.drawable.ic_sssc)
                .setColor(Color.RED)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setWhen(event.getNotificationTime())
                .setShowWhen(true)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setContentIntent(resultPendingIntent);
        return builder.build();
    }

    public void loadEventImage() {
        //start a background thread for networking
        new Thread(() -> {
            try {
                //download the drawable
                final Drawable drawable = Drawable.createFromStream((InputStream) new URL(event.getImageURL()).getContent(), "src");
                //edit the view in the UI thread
                imageView.post(() -> imageView.setImageDrawable(drawable));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendEventRegistrationEmail() {
        MainApplication appSettings = (MainApplication) requireActivity().getApplication();
        if (appSettings.hasStudentInformation()) {
            EmailBuilder.confirmSendEmail(requireActivity(), EmailBuilder.EmailType.EVENT_REGISTRATION, event);
        } else {
            EmailBuilder.showStudentNameDialog(requireActivity(), EmailBuilder.EmailType.EVENT_REGISTRATION, event);
        }
    }

    @Override
    public void onResume() {
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Events");
        super.onResume();
    }
}
