package ghelani.kshamina.sssc_android_app.ui.event;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;

@AndroidEntryPoint
public class EventsFragment extends Fragment {

    private List<DiffItem> items = new ArrayList<>();
    MainListAdapter adapter;

    public EventsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter = new MainListAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventListViewModel eventListViewModel = new ViewModelProvider(this).get(EventListViewModel.class);

        eventListViewModel.getEvents().observe(getViewLifecycleOwner(), events -> {
            items.clear();
            items.addAll(events);
            adapter.notifyDataSetChanged();
        });

        eventListViewModel.getNavigationEvent().observe(getViewLifecycleOwner(), fragment ->
                ((MainActivity) requireActivity()).replaceFragment(fragment));

        eventListViewModel.fetchEvents();
    }

}

