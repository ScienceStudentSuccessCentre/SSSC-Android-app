package ghelani.kshamina.sssc_android_app;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static android.os.SystemClock.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        sleep(200);
        startActivity(intent);
        finish();
    }
}
