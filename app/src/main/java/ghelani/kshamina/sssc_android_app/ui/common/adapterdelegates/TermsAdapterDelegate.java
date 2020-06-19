package ghelani.kshamina.sssc_android_app.ui.common.adapterdelegates;

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
import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.ui.common.list.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.ListViewModel;

public class TermsAdapterDelegate extends AdapterDelegate<List<DiffItem>> {
    private LayoutInflater inflater;

    public TermsAdapterDelegate(Activity activity, ListViewModel viewModel) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof Term;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ListViewHolder(inflater.inflate(R.layout.item_term, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        ListViewHolder listViewHolder = (ListViewHolder) holder;

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
