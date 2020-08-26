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

import java.util.Collections;

import dagger.hilt.android.AndroidEntryPoint;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.utils.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.utils.list.SwipeToDeleteCallback;

@AndroidEntryPoint
public class TermsFragment extends Fragment {

    private TermsViewModel termsViewModel;

    private RecyclerView recyclerView;

    private TextView emptyTermsMessage;

    private MenuItem deleteItem;

    private MenuItem cancelDeleteItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
        ItemTouchHelper swipeHelper = new ItemTouchHelper(new SwipeToDeleteCallback(getContext(),adapter, recyclerView, (index -> termsViewModel.deleteTerm(index))));
        swipeHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        termsViewModel = new ViewModelProvider(this).get(TermsViewModel.class);
        termsViewModel.state.observe(this, termViewState -> {
            if (termViewState.isLoading()) {
                System.out.println("Terms Loading");
            } else if (termViewState.isError()) {
                System.out.println("Terms ERROR: " + termViewState.getError());
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

        termsViewModel.termSelected.observe(this, term -> {
            setHasOptionsMenu(false);
            ((MainActivity) requireActivity()).replaceFragment(CourseListFragment.newInstance(term));
        });

        termsViewModel.fetchTerms();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.term_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.deleteActionItem) {
            if (termsViewModel.isDeleteMode()) {
                item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete));

            } else {
                item.setIcon(R.drawable.ic_close);
            }

            termsViewModel.toggleDeleteMode();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddTermScreen() {
        ((MainActivity) requireActivity()).replaceFragment(AddTermFragment.newInstance());
    }

    @Override
    public void onResume() {
        setHasOptionsMenu(true);
        termsViewModel.fetchTerms();
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Terms");
        super.onResume();
    }

    @Override
    public void onPause() {
        setHasOptionsMenu(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
