package ghelani.kshamina.sssc_android_app.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ghelani.kshamina.sssc_android_app.GradesFragment;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ResourcesFragment;

public class EventSingleActivity extends AppCompatActivity {
    private Event event;
    final Fragment fragment1 = new EventsFragment();
    final Fragment fragment2 = new GradesFragment();
    final Fragment fragment3 = new ResourcesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    public EventSingleActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_single);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Event event = (Event) getIntent().getSerializableExtra("event");
//        toolbar.setTitle(event.getEvent());
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(a);
                        return true;

                    case R.id.navigation_dashboard:
//                    fm.beginTransaction().hide(active).show(fragment2).commit();
//                    active = fragment2;
//                    getSupportActionBar().show();
//                    toolbar.setTitle("Terms");
//                    return true;
                        Intent b = new Intent(getBaseContext(), EventSingleActivity.class);
                        startActivity(b);
                        return true;

                    case R.id.navigation_notifications:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        getSupportActionBar().hide();
                        return true;
                }
                return false;
                }
            });
        }
}
