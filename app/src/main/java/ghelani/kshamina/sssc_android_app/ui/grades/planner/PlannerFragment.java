package ghelani.kshamina.sssc_android_app.ui.grades.planner;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.Grading;

public class PlannerFragment extends Fragment {
    // Variables for the term CGPA part
    private EditText currentCGPAText;
    private EditText creditsCompleteText;
    private EditText desiredCGPAText;
    private EditText creditsInProgressText;
    private TextView calculatedCGPAText;

    // Variables for the Overall CGPA part
    private EditText overallCurrentCGPAText;
    private EditText overallCreditsCompleteText;
    private EditText overallPredictedCGPAText;
    private EditText overallCreditsProgressText;
    private TextView overallCGPAText;

    private TextWatcher termTextWatcher = new TextWatcher() {
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

                double result = Grading.calculateRequiredCGPA(currentCGPA, creditsComplete, desiredCGPA, creditsInProgress);
                calculatedCGPAText.setText(String.format(Locale.CANADA, "%.1f", result));
            } else {
                calculatedCGPAText.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    private TextWatcher overallTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String currentCGPATest = overallCurrentCGPAText.getText().toString();
            String creditsCompleteTest = overallCreditsCompleteText.getText().toString();
            String predictedCGPATest = overallPredictedCGPAText.getText().toString();
            String creditsInProgressTest = overallCreditsProgressText.getText().toString();

            if ( (currentCGPATest.length() > 0) && (creditsCompleteTest.length() > 0) &&
                    (predictedCGPATest.length() > 0) && (creditsInProgressTest.length() > 0) ) {
                double currentCGPA = Double.parseDouble(currentCGPATest);
                double creditsComplete = Double.parseDouble(creditsCompleteTest);
                double predictedCGPA = Double.parseDouble(predictedCGPATest);
                double creditsInProgress = Double.parseDouble(creditsInProgressTest);

                double result = Grading.calculatePredictedCGPA(currentCGPA, creditsComplete, predictedCGPA, creditsInProgress);
                overallCGPAText.setText(String.format(Locale.CANADA, "%.1f", result));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup plannerView = (ViewGroup) inflater.inflate(R.layout.fragment_planner, container, false);

//        Toolbar toolbar = getParentFragment().getView().findViewById(R.id.gradesToolbar);
//        toolbar.setTitle("Planner");

        currentCGPAText = plannerView.findViewById(R.id.currentCGPA);
        currentCGPAText.addTextChangedListener(termTextWatcher);

        creditsCompleteText = plannerView.findViewById(R.id.creditsComplete);
        creditsCompleteText.addTextChangedListener(termTextWatcher);

        desiredCGPAText = plannerView.findViewById(R.id.desiredCGPA);
        desiredCGPAText.addTextChangedListener(termTextWatcher);

        creditsInProgressText = plannerView.findViewById(R.id.creditsProgress);
        creditsInProgressText.addTextChangedListener(termTextWatcher);

        calculatedCGPAText = plannerView.findViewById(R.id.calculatedCGPA);


        overallCurrentCGPAText = plannerView.findViewById(R.id.overallCurrentCGPA);
        overallCurrentCGPAText.addTextChangedListener(overallTextWatcher);

        overallCreditsCompleteText = plannerView.findViewById(R.id.overallCreditsComplete);
        overallCreditsCompleteText.addTextChangedListener(overallTextWatcher);

        overallPredictedCGPAText = plannerView.findViewById(R.id.overallPredictedCGPA);
        overallPredictedCGPAText.addTextChangedListener(overallTextWatcher);

        overallCreditsProgressText = plannerView.findViewById(R.id.overallCreditsProgress);
        overallCreditsProgressText.addTextChangedListener(overallTextWatcher);

        overallCGPAText = plannerView.findViewById(R.id.overallCGPA);


        return plannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Planner");
    }
}
