package ghelani.kshamina.sssc_android_app.ui.common.list.adapterdelegates;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TextItem;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.TwoLineItem;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class TwoLineItemAdapterDelegate extends AdapterDelegate<List<DiffItem>> {

    private LayoutInflater inflater;

    public TwoLineItemAdapterDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @Override
    protected boolean isForViewType(@NonNull List<DiffItem> items, int position) {
        return items.get(position) instanceof TwoLineItem;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new TwoLineItemViewHolder(inflater.inflate(R.layout.mentor_item, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DiffItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        TwoLineItem item = (TwoLineItem) items.get(position);

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

        public void onBind(TwoLineItem item){
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
