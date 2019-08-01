package ghelani.kshamina.sssc_android_app.event;

import android.os.Bundle;

import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;

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
