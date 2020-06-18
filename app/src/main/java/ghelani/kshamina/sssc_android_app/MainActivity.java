package ghelani.kshamina.sssc_android_app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.facebook.stetho.Stetho;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import ghelani.kshamina.sssc_android_app.ui.ResourcesFragment;
import ghelani.kshamina.sssc_android_app.ui.SettingsFragment;
import ghelani.kshamina.sssc_android_app.ui.event.EventsFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.GradesFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigatonView;
    Toolbar toolbar;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigatonView = findViewById(R.id.navigation);
        navigatonView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        changeFragment(new EventsFragment());
        setupBottomNav();

        // Initialize settings with its default values
        // false means do not override user's saved settings on start, if they exist
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    public void changeFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, newFragment);
        fragmentTransaction.commit();
    }

    private void setupBottomNav() {
        navigatonView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeFragment(new EventsFragment());
                    getSupportActionBar().setTitle("Events");
                    break;
                case R.id.navigation_dashboard:
                    changeFragment(new GradesFragment());
                    getSupportActionBar().setTitle("Grades Calculator");
                    break;
                case R.id.navigation_notifications:
                    changeFragment(new ResourcesFragment());
                    getSupportActionBar().setTitle("Resources");
                    break;
                case R.id.navigation_settings:
                    changeFragment(new SettingsFragment());
                    getSupportActionBar().setTitle("Settings");
            }
            return true;
        });
    }

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
