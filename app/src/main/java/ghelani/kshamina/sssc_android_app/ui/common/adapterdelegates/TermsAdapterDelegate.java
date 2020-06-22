package ghelani.kshamina.sssc_android_app.ui.common.adapterdelegates;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.ui.common.list.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.model.TermItem;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list.TermsViewModel;

/**
 * List adapter for the Terms list
 */
public class TermsAdapterDelegate extends AdapterDelegate<List<DiffItem>> {
    private LayoutInflater inflater;
    private TermsViewModel viewModel;

    public TermsAdapterDelegate(Activity activity, ViewModel viewModel) {
        inflater = activity.getLayoutInflater();
        this.viewModel = (TermsViewModel) viewModel;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof TermItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ListViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TermItem term = (TermItem) items.get(position);
        ListViewHolder listViewHolder = (ListViewHolder) holder;
        listViewHolder.itemCard.setOnLongClickListener(v -> viewModel.onItemLongClicked(new Term(term)));
        listViewHolder.itemCard.setOnClickListener(v -> viewModel.onItemClicked(new Term(term)));
        listViewHolder.deleteIcon.setOnClickListener(v->viewModel.deleteItem(new Term(term)));
        listViewHolder.deleteIcon.setVisibility(viewModel.isDeleteMode.getValue()? View.VISIBLE: View.GONE);
        listViewHolder.shortForm.setText(term.asShortString());
        listViewHolder.description.setText(term.toString());
        listViewHolder.heading.setVisibility(View.GONE);
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
