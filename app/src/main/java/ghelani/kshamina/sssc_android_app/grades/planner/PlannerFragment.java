package ghelani.kshamina.sssc_android_app.grades.planner;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.grades.Grading;

public class PlannerFragment extends Fragment {
    private EditText currentCGPAText;

    private TextWatcher overallTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            double currentCGPA = Double.parseDouble(currentCGPAText.getText().toString());
            System.out.println(currentCGPA);
            Grading.calculateRequiredCGPA(currentCGPA, 1, 1, 1);
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup plannerView = (ViewGroup) inflater.inflate(R.layout.fragment_planner, container, false);

        currentCGPAText = plannerView.findViewById(R.id.currentCGPA);
        currentCGPAText.addTextChangedListener(overallTextWatcher);

        return plannerView;
    }
}
