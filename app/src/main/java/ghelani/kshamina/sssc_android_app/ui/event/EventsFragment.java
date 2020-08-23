package ghelani.kshamina.sssc_android_app.ui.event;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;

public class EventsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    public EventsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        MainListAdapter listAdapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(listAdapter);

        EventListViewModel eventListViewModel = new ViewModelProvider(this, viewModelFactory).get(EventListViewModel.class);

        eventListViewModel.getEvents().observe(this, events -> {
            listAdapter.setItems(events);
            listAdapter.notifyDataSetChanged();
        });

        eventListViewModel.getNavigationEvent().observe(this, fragment ->
                ((MainActivity) requireActivity()).replaceFragment(fragment));

        eventListViewModel.fetchEvents();
    }

}

