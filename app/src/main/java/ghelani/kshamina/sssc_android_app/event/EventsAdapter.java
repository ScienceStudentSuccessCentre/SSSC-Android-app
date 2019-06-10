package ghelani.kshamina.sssc_android_app.event;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {
    private List<Event> eventsList;

    public EventsAdapter(List<Event> events) {
        this.eventsList = events;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event, date;

        public MyViewHolder(View view) {
            super(view);
            event = (TextView) view.findViewById(R.id.result);
            date = (TextView) view.findViewById(R.id.dates);
        }
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
        holder.event.setText(event.getEvent());
        holder.date.setText(event.getDate());
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

}
