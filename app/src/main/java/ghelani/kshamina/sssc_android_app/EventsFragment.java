package ghelani.kshamina.sssc_android_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class EventsFragment extends Fragment {
    private TextView result;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        result = (TextView) view.findViewById(R.id.result);
        getWebsite();
        return view;
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                String url = "https://sssc.carleton.ca/events";

                try {
                    Document doc = Jsoup.connect(url).get();
                    String title = doc.title();
                    Elements events = doc.select(".event-details--title");
                    Elements dates = doc.select(".event-details--date");

                    builder.append(title).append("\n");

                    int i = 0;
                    for (Element event : events) {
                        builder.append("\n").append(event.text()).append("    " + dates.get(i).text());
                        i++;
                    }

                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}

