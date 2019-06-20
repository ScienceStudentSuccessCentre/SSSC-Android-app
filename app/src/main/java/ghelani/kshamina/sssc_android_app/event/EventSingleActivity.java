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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import ghelani.kshamina.sssc_android_app.GradesFragment;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ResourcesFragment;

public class EventSingleActivity extends MainActivity {
    private Event event;
    public EventSingleActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LinearLayout dynamicContent = (LinearLayout) findViewById(R.id.dynamicContent);
//        View wizard = getLayoutInflater().inflate(R.layout.event_single, null);
//        dynamicContent.addView(wizard);

    }
}
