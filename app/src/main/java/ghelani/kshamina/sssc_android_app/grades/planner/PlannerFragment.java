package ghelani.kshamina.sssc_android_app.grades.planner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ghelani.kshamina.sssc_android_app.R;

public class PlannerFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup plannerView = (ViewGroup) inflater.inflate(R.layout.fragment_planner, container,false);
        return plannerView;
    }
}
