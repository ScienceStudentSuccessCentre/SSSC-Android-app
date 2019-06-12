package ghelani.kshamina.sssc_android_app.event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.text.DateFormat;
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

        result = (TextView) view.findViewById(R.id.result);
        dates = (TextView) view.findViewById(R.id.dates);

        getWebsite();
        return view;
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builderDates = new StringBuilder();

                String url = "https://sssc.carleton.ca/events";

                try {
                    Document doc = Jsoup.connect(url).get();
                    String title = doc.title();
                    Elements events = doc.select(".event-details--title");
                    Elements dates = doc.select(".event-details--date");

                    builder.append(title).append("\n");

                    int i = 0;
                    for (Element event : events) {
                        builder.append("\n").append(event.text());
                        builderDates.append("\n").append(dates.get(i).text());
                        eventList.add(new Event(event.text(), Event.stringToDate(dates.get(i).text())));
                        i++;
                    }

                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        result.setText(builder.toString());
//                        dates.setText(builderDates.toString());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }
}

