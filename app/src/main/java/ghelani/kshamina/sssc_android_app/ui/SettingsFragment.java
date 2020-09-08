package ghelani.kshamina.sssc_android_app.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ghelani.kshamina.sssc_android_app.R;

public class SettingsFragment extends Fragment {

    // Settings keys -- these should match the keys defined in settings.xml
    public static final String KEY_PREF_INCLUDE_IN_PROGRESS_COURSES = "include_in_progress_switch";

    // Data model
    private SharedPreferences settings;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup settingsView = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container,false);

        // Inflate components
        // Components
        Switch inProgressCoursesFilterSwitch = settingsView.findViewById(R.id.include_in_progress_switch);
      //  Button backupGradesButton = settingsView.findViewById(R.id.backup_grades_button);

        // Add action listeners
        inProgressCoursesFilterSwitch.setOnCheckedChangeListener(this::handleInProgressCoursesFilterSwitchChanged);
      //  backupGradesButton.setOnClickListener(this::backupGrades);

        // Load settings defaults from last saved
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        inProgressCoursesFilterSwitch.setChecked(settings.getBoolean(KEY_PREF_INCLUDE_IN_PROGRESS_COURSES, true));

        return settingsView;
    }

    private void handleInProgressCoursesFilterSwitchChanged(CompoundButton buttonView, boolean isChecked) {
        settings.edit().putBoolean(KEY_PREF_INCLUDE_IN_PROGRESS_COURSES, isChecked).apply();
    }

    private void backupGrades(View button) {
        // TODO implement
    }
}
