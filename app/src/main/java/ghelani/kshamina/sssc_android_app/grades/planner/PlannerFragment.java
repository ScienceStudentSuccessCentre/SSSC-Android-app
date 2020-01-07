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
import android.widget.TextView;

import java.util.Locale;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.grades.Grading;

public class PlannerFragment extends Fragment {
    private EditText currentCGPAText;
    private EditText creditsCompleteText;
    private EditText desiredCGPAText;
    private EditText creditsInProgressText;
    private TextView calculatedCGPAText;

    private TextWatcher overallTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String currentCGPATest = currentCGPAText.getText().toString();
            String creditsCompleteTest = creditsCompleteText.getText().toString();
            String desiredCGPATest = desiredCGPAText.getText().toString();
            String creditsInProgressTest = creditsInProgressText.getText().toString();

            if ( (currentCGPATest.length() > 0) && (creditsCompleteTest.length() > 0) &&
                    (desiredCGPATest.length() > 0) && (creditsInProgressTest.length() > 0) ) {
                double currentCGPA = Double.parseDouble(currentCGPATest);
                double creditsComplete = Double.parseDouble(creditsCompleteTest);
                double desiredCGPA = Double.parseDouble(desiredCGPATest);
                double creditsInProgress = Double.parseDouble(creditsInProgressTest);

                System.out.println(currentCGPA);
                double result = Grading.calculateRequiredCGPA(currentCGPA, creditsComplete, desiredCGPA, creditsInProgress);
                System.out.println("This is the required CGPA" + result);
                calculatedCGPAText.setText(String.format(Locale.CANADA, "%.1f", result));
            } else {
                calculatedCGPAText.setText("");
            }
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

        creditsCompleteText = plannerView.findViewById(R.id.creditsComplete);
        creditsCompleteText.addTextChangedListener(overallTextWatcher);

        desiredCGPAText = plannerView.findViewById(R.id.desiredCGPA);
        desiredCGPAText.addTextChangedListener(overallTextWatcher);

        creditsInProgressText = plannerView.findViewById(R.id.creditsProgress);
        creditsInProgressText.addTextChangedListener(overallTextWatcher);

        calculatedCGPAText = plannerView.findViewById(R.id.calculatedCGPA);


        return plannerView;
    }
}
