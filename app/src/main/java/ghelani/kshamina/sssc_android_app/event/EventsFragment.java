package ghelani.kshamina.sssc_android_app.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.SettingsActivity;

public class EventsFragment extends Fragment {
    private TextView result;
    private TextView dates;

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

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
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        setHasOptionsMenu(false);
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

        getEventData();

        return view;
    }

    private void getEventData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                eventList.clear();

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
        Toast.makeText(getContext(), "You Clicked: " + event.getName(), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putSerializable("event", event);
        Fragment eventSingle = new EventSingleFragment();
        eventSingle.setArguments(bundle);
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, eventSingle).addToBackStack(null).commit();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.event_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

}

