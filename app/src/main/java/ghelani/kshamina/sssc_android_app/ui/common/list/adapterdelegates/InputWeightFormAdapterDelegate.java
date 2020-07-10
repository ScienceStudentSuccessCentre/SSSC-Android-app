package ghelani.kshamina.sssc_android_app.ui.common.list.adapterdelegates;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.WeightItem;

public class InputWeightFormAdapterDelegate extends AdapterDelegate<List<DiffItem>> {
    private LayoutInflater inflater;

    public InputWeightFormAdapterDelegate(Activity activity) {
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof WeightItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new InputWeightFormAdapterDelegate.InputWeightViewHolder(inflater.inflate(R.layout.input_weight_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        WeightItem item = (WeightItem) items.get(position);
        InputWeightFormAdapterDelegate.InputWeightViewHolder weightItemViewHolder = (InputWeightFormAdapterDelegate.InputWeightViewHolder) holder;

        if (!item.getName().isEmpty()) {
            weightItemViewHolder.name.setText(item.getName());
        } else {
            weightItemViewHolder.name.setHint("Final Exam");
        }

        if (!item.getValue().isEmpty()) {
            weightItemViewHolder.value.setText(item.getValue());
        } else {
            weightItemViewHolder.value.setHint("30%");
        }

        weightItemViewHolder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.getNameListener().onValueChanged(item,s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        weightItemViewHolder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item.getNameListener().onValueChanged(item,s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    static class InputWeightViewHolder extends RecyclerView.ViewHolder {

        public EditText name;
        public EditText value;

        public InputWeightViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameInputField);
            value = itemView.findViewById(R.id.valueInputField);
            value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
    }
}
