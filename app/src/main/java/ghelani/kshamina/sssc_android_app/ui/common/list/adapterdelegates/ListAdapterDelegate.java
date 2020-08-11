package ghelani.kshamina.sssc_android_app.ui.common.list.adapterdelegates;

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
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.ListItem;

public class ListAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public ListAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof ListItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ListAdapterDelegate.ListViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ListItem item = (ListItem) items.get(position);
        ListViewHolder listViewHolder = (ListViewHolder) holder;

        listViewHolder.itemCard.setOnClickListener(v -> item.getClickListener().onItemClicked(item.getId()));
        listViewHolder.itemCard.setOnLongClickListener(v -> item.getClickListener().onItemLongClicked());
        listViewHolder.deleteIcon.setOnClickListener(v -> item.getClickListener().deleteItem(item.getId()));
        listViewHolder.deleteIcon.setVisibility(item.isDeleteIconVisible() ? View.VISIBLE : View.GONE);
        listViewHolder.shortForm.setText((item.getShortFormText() == null || item.getShortFormText().isEmpty()) ? "N/A" : item.getShortFormText());
        listViewHolder.description.setText(item.getDescriptionText());
        listViewHolder.heading.setText(item.getHeaderText());
        listViewHolder.heading.setVisibility(item.getHeaderText().isEmpty() ? View.GONE : View.VISIBLE);
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        public MaterialCardView itemCard;
        public ImageView deleteIcon;
        public TextView shortForm;
        public TextView description;
        public TextView heading;

        public ListViewHolder(View itemView) {
            super(itemView);
            itemCard = itemView.findViewById(R.id.cardItem);
            deleteIcon = itemView.findViewById(R.id.deleteButton);
            shortForm = itemView.findViewById(R.id.shortForm);
            description = itemView.findViewById(R.id.description);
            heading = itemView.findViewById(R.id.heading);
        }
    }
}
