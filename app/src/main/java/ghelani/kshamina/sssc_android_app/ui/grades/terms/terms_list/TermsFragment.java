package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.utils.list.SwipeToDeleteCallback;

@AndroidEntryPoint
public class TermsFragment extends Fragment {

    private TermsViewModel termsViewModel;

    private RecyclerView recyclerView;

    private TextView emptyTermsMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Terms");

        FloatingActionButton addTermBtn = view.findViewById(R.id.addTermFab);
        addTermBtn.setOnClickListener(v -> openAddTermScreen());

        emptyTermsMessage = view.findViewById(R.id.emptyTermsListText);

        recyclerView = view.findViewById(R.id.termsRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        MainListAdapter adapter = new MainListAdapter(getActivity(), Collections.emptyList());
        recyclerView.setAdapter(adapter);

        termsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termsViewModel.state.observe(getViewLifecycleOwner(), termViewState -> {
            if (termViewState.isLoading()) {
            } else if (termViewState.isError()) {
            } else if (termViewState.isSuccess()) {
                if (termViewState.getItems().isEmpty()) {
                    emptyTermsMessage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTermsMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setItems(termViewState.getItems());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        termsViewModel.termSelected.observe(getViewLifecycleOwner(), term -> {
            ((MainActivity) requireActivity()).replaceFragment(CourseListFragment.newInstance(term));
        });

        termsViewModel.fetchTerms();
    }

    private void openAddTermScreen() {
        ((MainActivity) requireActivity()).replaceFragment(AddTermFragment.newInstance());
    }

    @Override
    public void onResume() {
        termsViewModel.fetchTerms();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Terms");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
