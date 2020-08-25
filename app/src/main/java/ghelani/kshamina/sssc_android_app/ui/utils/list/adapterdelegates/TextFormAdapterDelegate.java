package ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.TextItem;

public class TextFormAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public TextFormAdapterDelegate(Activity activity) {
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof TextItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new TextFormAdapterDelegate.TextItemViewHolder(inflater.inflate(R.layout.text_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TextItem item = (TextItem) items.get(position);

        TextFormAdapterDelegate.TextItemViewHolder textItemViewHolder = (TextFormAdapterDelegate.TextItemViewHolder) holder;

        int margin = item.isMarginTop()? R.dimen.text_item_margin_top : R.dimen.text_item_margin_top;
        //textItemViewHolder.text.setPadding(R.dimen.activity_horizontal_margin,margin,R.dimen.activity_horizontal_margin,R.dimen.text_item_margin_top);
        textItemViewHolder.text.setPadding(20,20,20,20);
        textItemViewHolder.text.setText(item.getText());
    }

    static class TextItemViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public RelativeLayout layout;

        public TextItemViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.descriptionText);
            layout = itemView.findViewById(R.id.textItemlayout);
        }

    }
}
