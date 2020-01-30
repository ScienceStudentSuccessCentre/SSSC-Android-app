package ghelani.kshamina.sssc_android_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    // Settings keys -- these should match the keys defined in settings.xml
    public static final String KEY_PREF_INCLUDE_IN_PROGRESS_COURSES = "include_in_progress_switch";

    // Components
    private Switch inProgressCoursesFilterSwitch;
    private Button backupGradesButton;

    // Data model
    private SharedPreferences settings;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup settingsView = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container,false);

        // Inflate components
        inProgressCoursesFilterSwitch = settingsView.findViewById(R.id.include_in_progress_switch);
        backupGradesButton = settingsView.findViewById(R.id.backup_grades_button);

        // Add action listeners
        inProgressCoursesFilterSwitch.setOnCheckedChangeListener(this::handleInProgressCoursesFilterSwitchChanged);
        backupGradesButton.setOnClickListener(this::backupGrades);

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
