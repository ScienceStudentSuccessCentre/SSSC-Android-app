package ghelani.kshamina.sssc_android_app.ui.grades.terms;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ghelani.kshamina.sssc_android_app.R;

public class TermsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup termsView = (ViewGroup) inflater.inflate(R.layout.fragment_terms, container,false);
        return termsView;
    }
}
