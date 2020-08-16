package ghelani.kshamina.sssc_android_app.ui.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.entity.Event;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.mentoring.MentorListViewModel;

public class EventsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private EventListViewModel eventListViewModel;

    private TextView result;
    private TextView dates;

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Event test;

    SharedPreferences preferences;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Event event = eventList.get(position);
            openEventSingle(event, view);
        }
    };

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    public EventsFragment() {
//        testEvent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        MainListAdapter listAdapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(listAdapter);

        eventListViewModel = new ViewModelProvider(this, viewModelFactory).get(EventListViewModel.class);

        eventListViewModel.getEvents().observe(this, events -> {
            listAdapter.setItems(events);
            listAdapter.notifyDataSetChanged();
        });

        eventListViewModel.getNavigationEvent().observe(this, fragment ->
                ((MainActivity) requireActivity()).replaceFragment(fragment));

        eventListViewModel.fetchEvents();
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
        String formattedDate = dateFormat.format(date);

/*
        // Required empty public constructor
        test = new Event("test", "Test Event", url,
                "describe", date, formattedDate, "SSSC (3431 Herzberg)",
                "https://sssc.carleton.ca/sites/default/files/inline-images/AG8Q9617_0.jpg", "wow");

 */
    }

}

