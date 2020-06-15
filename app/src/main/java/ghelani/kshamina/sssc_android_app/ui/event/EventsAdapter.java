package ghelani.kshamina.sssc_android_app.ui.event;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import ghelani.kshamina.sssc_android_app.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Event> eventsList;
    private View.OnClickListener onItemClickListener;

    public EventsAdapter(List<Event> events) {
        this.eventsList = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventsList.get(position);
        holder.event.setText(event.getName());

        String date = event.getDateDisplayString();
        SpannableString dateSize = new SpannableString(date);
        dateSize.setSpan(new RelativeSizeSpan(1.4f), dateSize.length() - 2, dateSize.length(), 0);
        holder.date.setText(dateSize);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event, date;

        public MyViewHolder(View view) {
            super(view);
            event = view.findViewById(R.id.result);
            date = view.findViewById(R.id.dates);

            view.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

}
