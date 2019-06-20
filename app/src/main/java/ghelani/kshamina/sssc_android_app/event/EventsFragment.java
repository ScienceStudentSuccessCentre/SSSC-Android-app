package ghelani.kshamina.sssc_android_app.event;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ghelani.kshamina.sssc_android_app.R;

public class EventsFragment extends Fragment {
    private TextView result;
    private TextView dates;

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Event event = eventList.get(position);
            openEventSingle(event, view);
        }
    };

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);

        adapter = new EventsAdapter(eventList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        result = (TextView) view.findViewById(R.id.result);
        dates = (TextView) view.findViewById(R.id.dates);

        getWebsite();
        return view;
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventList.clear();
                String url = "https://sssc.carleton.ca/events";

                try {
                    Document doc = Jsoup.connect(url).get();
                    String title = doc.title();
                    Elements events = doc.select(".event-details--title");
                    Elements dates = doc.select(".event-details--date");
                    Elements eventUrl = doc.select(".event-listing--list-item a");

                    int i = 0;
                    for (Element event : events) {
                        if(!eventList.contains(event)) {
                            Event newEvent = new Event();
                            newEvent.setEvent(event.text());
                            newEvent.setDate(Event.stringToDate(dates.get(i).text()));
                            newEvent.setDescription(getEventDescription("https://sssc.carleton.ca/" + eventUrl.get(i).attr("href")));
                            eventList.add(newEvent);
                        }
                        i++;
                    }

                } catch (IOException e) {

                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    private void openEventSingle(Event event, View view) {
        Toast.makeText(getContext(), "You Clicked: " + event.getEvent(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        Fragment eventSingle = new EventSingleFragment();
        eventSingle.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, eventSingle).addToBackStack(null).commit();
    }

    private String getEventDescription(String url) {
        String description = "";
        try {
            Document doc = Jsoup.connect(url).get();
            Element desc = doc.select(".event-full .event--description").first();
            description = desc.text();

        } catch(IOException e){
            System.out.println(url);
        }
        return description;

    }
}

