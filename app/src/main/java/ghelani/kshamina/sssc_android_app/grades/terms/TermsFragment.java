package ghelani.kshamina.sssc_android_app.grades.terms;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Term;
import ghelani.kshamina.sssc_android_app.event.Event;
import ghelani.kshamina.sssc_android_app.event.EventSingleFragment;
import ghelani.kshamina.sssc_android_app.event.EventsAdapter;

public class TermsFragment extends Fragment {

    private List<Term> termList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TermsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    SharedPreferences preferences;

    final String url = "http://sssc-carleton-app-server.herokuapp.com/terms";


    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Term term = termList.get(position);
            openTermSingle(term, view);// TODO implement this...
        }
    };




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup termsView = (ViewGroup) inflater.inflate(R.layout.fragment_terms, container,false);

        setHasOptionsMenu(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        recyclerView = termsView.findViewById(R.id.eventsList);
        recyclerView.setHasFixedSize(true);

//        adapter = new EventsAdapter(termList); //TODO terms adapter
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        getTermData();

        return termsView;
    }


    //TODO: Implement opening a term
    private void openTermSingle(Term term, View view) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("term", term);
//        Fragment termSingle = new EventSingleFragment();
//        eventSingle.setArguments(bundle);
//        AppCompatActivity activity = (AppCompatActivity) view.getContext();
//        activity.invalidateOptionsMenu();
//        activity.getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_container, eventSingle).addToBackStack(null).commit();

    }



    private void getTermData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                termList.clear();
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
//                                termList.add(new Term(jsonArray.getJSONObject(i)));
                            }
                        } catch (JSONException e) {
                            System.out.println("ERROR");
                        }
//                        Collections.sort(eventList);
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
}
