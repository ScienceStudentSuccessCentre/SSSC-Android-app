package ghelani.kshamina.sssc_android_app.event;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ghelani.kshamina.sssc_android_app.GradesFragment;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ResourcesFragment;

public class EventSingleActivity extends AppCompatActivity {
    private Event event;

    public EventSingleActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_single);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Event event = (Event) getIntent().getSerializableExtra("event");
        toolbar.setTitle(event.getEvent());
        setSupportActionBar(toolbar);

    }
}
