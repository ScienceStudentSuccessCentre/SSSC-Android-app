package ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates;

import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.InputItem;

public class InputFormAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public InputFormAdapterDelegate(Activity activity) {
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof InputItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new InputFormAdapterDelegate.InputItemViewHolder(inflater.inflate(R.layout.input_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        InputItem item = (InputItem) items.get(position);
        InputItemViewHolder inputItemViewHolder = (InputItemViewHolder) holder;

        switch (item.getType()) {
            case TEXT:
                inputItemViewHolder.name.setText(item.getName());
                inputItemViewHolder.textInput.setEnabled(item.isEnabled());
                inputItemViewHolder.textInput.setHint(item.getHint());
                inputItemViewHolder.textInput.setText(item.getValue());
                inputItemViewHolder.textInput.setInputType(item.getKeyboardType());
                inputItemViewHolder.itemView.setOnClickListener(v -> inputItemViewHolder.textInput.requestFocus());
                if (item.getName().equals("Code")) {
                    inputItemViewHolder.textInput.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                }
                inputItemViewHolder.switchInput.setVisibility(View.GONE);
                inputItemViewHolder.textInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.getListener().onValueChanged(item, inputItemViewHolder.textInput.getText().toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                break;
            case BUTTON:
                inputItemViewHolder.inputLayout.setVisibility(View.GONE);
                inputItemViewHolder.inputButton.setVisibility(View.VISIBLE);
                inputItemViewHolder.inputButton.setText(item.getName());
                inputItemViewHolder.inputButton.setOnClickListener(v -> item.getListener().onValueChanged(item, "pressed"));
                break;
            case SWITCH:
                inputItemViewHolder.name.setText(item.getName());
                inputItemViewHolder.textInput.setVisibility(View.GONE);
                inputItemViewHolder.switchInput.setVisibility(View.VISIBLE);
                inputItemViewHolder.switchInput.setChecked(Boolean.parseBoolean(item.getValue()));
                inputItemViewHolder.switchInput.setOnCheckedChangeListener((buttonView, isChecked) -> item.getListener().onValueChanged(item, String.valueOf(isChecked)));
                break;

            case SELECTION_SCREEN:
                inputItemViewHolder.name.setText(item.getName());
                inputItemViewHolder.textInput.setHint(item.getHint());
                inputItemViewHolder.textInput.setText(item.getValue());
                inputItemViewHolder.textInput.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_right_24, 0);
                inputItemViewHolder.textInput.setOnClickListener(v ->
                        item.getListener().onValueChanged(item, inputItemViewHolder.textInput.getText().toString()));
                inputItemViewHolder.textInput.setEnabled(false);
                inputItemViewHolder.switchInput.setVisibility(View.GONE);
                inputItemViewHolder.itemView.setOnClickListener(v ->
                        item.getListener().onValueChanged(item, inputItemViewHolder.textInput.getText().toString()));
                break;
        }
    }

    public static class InputItemViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public TextView name;
        public EditText textInput;
        public SwitchMaterial switchInput;
        public ConstraintLayout inputLayout;
        public MaterialCardView itemLayout;
        public Button inputButton;

        public InputItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.inputPrompt);
            itemLayout = itemView.findViewById(R.id.itemLayout);
            textInput = itemView.findViewById(R.id.userInputField);
            switchInput = itemView.findViewById(R.id.switchInput);
            inputLayout = itemView.findViewById(R.id.standardInputLayout);
            inputButton = itemView.findViewById(R.id.inputButton);
        }
    }
}
