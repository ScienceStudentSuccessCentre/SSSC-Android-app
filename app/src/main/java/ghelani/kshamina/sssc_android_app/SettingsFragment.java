package ghelani.kshamina.sssc_android_app;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    // Settings keys -- these should match the keys defined in settings.xml
    public static final String KEY_PREF_INCLUDE_IN_PROGRESS_COURSES = "include_in_progress_switch";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

    // TODO use custom layout
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewGroup settingsView = (ViewGroup) inflater.inflate(R.layout.fragment_settings, container,false);
//
//        // Inflate widget
//        inProgressCoursesSwitch = settingsView.findViewById(R.id.include_in_progress_switch);
//
////        // Load setting
////        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
////        boolean shouldInclude = prefs.getBoolean(PREF_INCLUDE_IN_PROGRESS_COURSES, true);
////        inProgressCoursesSwitch.setChecked(shouldInclude);
////
////        // Add onchange listener
////        inProgressCoursesSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> updatePreferences());
////
//        return settingsView;
//    }

}




