package ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.MentorItem;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MentorAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public MentorAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof MentorItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new TwoLineItemViewHolder(inflater.inflate(R.layout.mentor_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        MentorItem item = (MentorItem) items.get(position);

        TwoLineItemViewHolder twoLineItemViewHolder = (TwoLineItemViewHolder) holder;
        twoLineItemViewHolder.onBind(item);
    }

    static class TwoLineItemViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView content;
        public ImageView image;
        public View itemView;

        public TwoLineItemViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            header = itemView.findViewById(R.id.heading);
            content = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.avatar);
        }

        public void onBind(MentorItem item){
            itemView.setOnClickListener(v -> item.getClickListener().onItemClicked());
            header.setText(item.getHeader());
            content.setText(item.getDescription());
            Picasso.get()
                    .load(item.getImageUrl())
                    .transform(new RoundedCornersTransformation(250,0))
                    .into(image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
