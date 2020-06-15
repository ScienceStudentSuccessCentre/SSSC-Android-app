package ghelani.kshamina.sssc_android_app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.facebook.stetho.Stetho;

import ghelani.kshamina.sssc_android_app.ui.ResourcesFragment;
import ghelani.kshamina.sssc_android_app.ui.SettingsFragment;
import ghelani.kshamina.sssc_android_app.ui.event.Event;
import ghelani.kshamina.sssc_android_app.ui.event.EventSingleFragment;
import ghelani.kshamina.sssc_android_app.ui.event.EventsFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;

public class MainActivity extends AppCompatActivity {
    final EventsFragment fragment1 = new EventsFragment();
    final Fragment fragment2 = new GradesFragment();
    final Fragment fragment3 = new ResourcesFragment();
    final Fragment fragment4 = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Upcoming Events");
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setLabelVisibilityMode(1);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container,fragment1, "1").commit();

        // If coming from notification click
        Event event = (Event) getIntent().getSerializableExtra("event");
        if(event != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("event", event);
            EventSingleFragment eventSingle = new EventSingleFragment();
            eventSingle.setArguments(bundle);
            fm.beginTransaction().replace(R.id.main_container, eventSingle).addToBackStack(null).commit();

        }

        // Initialize settings with its default values
        // false means do not override user's saved settings on start, if they exist
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fm.popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    getSupportActionBar().show();
                    toolbar.setTitle("Upcoming Events");
                    return true;

                case R.id.navigation_dashboard:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    getSupportActionBar().show();
//                    toolbar.setTitle("Terms");
                    toolbar.setTitle("Grades Calculator");
                    return true;

                case R.id.navigation_notifications:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    getSupportActionBar().hide();
                    return true;

                case R.id.navigation_settings:
                    fm.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    getSupportActionBar().hide();
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.setGroupVisible(R.id.event_single_menu, false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        return true;
    }

    @Override
    public void onBackPressed (){
        WebView webView = findViewById(R.id.webView);
        if(webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

}
