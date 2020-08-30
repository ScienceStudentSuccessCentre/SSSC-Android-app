package ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.SelectionItem;

public class SelectionInputAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public SelectionInputAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }


    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof SelectionItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {

        return new SelectionListViewHolder(inflater.inflate(R.layout.item_new_term_option, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        SelectionItem optionItem = (SelectionItem) items.get(position);
        SelectionListViewHolder listViewHolder = (SelectionListViewHolder) holder;

        listViewHolder.itemCard.setOnClickListener(v -> optionItem.getClickListener().onItemSelected(optionItem.getIndex()));
        listViewHolder.optionValue.setText(optionItem.getValue());
        listViewHolder.selectedIcon.setVisibility(optionItem.isSelected() ? View.VISIBLE : View.GONE);
    }

    static class SelectionListViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView itemCard;
        public ImageView selectedIcon;
        public TextView optionValue;

        public SelectionListViewHolder(View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.selectionItem);
            selectedIcon = itemView.findViewById(R.id.checked);
            optionValue = itemView.findViewById(R.id.optionName);
        }
    }
}
