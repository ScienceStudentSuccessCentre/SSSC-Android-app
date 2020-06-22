package ghelani.kshamina.sssc_android_app.ui.event;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ghelani.kshamina.sssc_android_app.R;

public class EventsFragment extends Fragment {
    private TextView result;
    private TextView dates;

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Event test;

    SharedPreferences preferences;

    final String url = "http://sssc-carleton-app-server.herokuapp.com/events";

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
//        testEvent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerView = view.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);

        adapter = new EventsAdapter(eventList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        result = view.findViewById(R.id.result);
        dates = view.findViewById(R.id.dates);

        getEventData();

        return view;
    }

    private void getEventData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventList.clear();
//                eventList.add(test);
                RequestQueue ExampleRequestQueue = Volley.newRequestQueue(getContext());
                StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //This code is executed if the server responds, whether or not the response contains data.
                        //The String 'response' contains the server's response.
                        //You can test it by printing response.substring(0,500) to the screen.
                        System.out.println("*****" + response);
                        try {
                            // Convert response string to JSON array
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                eventList.add(new Event(jsonArray.getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            System.out.println("ERROR");
                        }
                        Collections.sort(eventList);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //This code is executed if there is an error.
                    }
                });

                ExampleRequestQueue.add(ExampleStringRequest);

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
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        Fragment eventSingle = new EventSingleFragment();
        eventSingle.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.invalidateOptionsMenu();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, eventSingle).addToBackStack(null).commit();
    }

    private void testEvent() {
        URL url = null;
        try {
            url = new URL("www.google.ca");
        } catch (MalformedURLException e) {

        }

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.add(Calendar.MINUTE, 1);

        date = calendar.getTime();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String formattedDate=dateFormat. format(date);


        // Required empty public constructor
        test = new Event("test", "Test Event", url,
                "describe", date, formattedDate, "SSSC (3431 Herzberg)",
                "https://sssc.carleton.ca/sites/default/files/inline-images/AG8Q9617_0.jpg", "wow");
    }

}
