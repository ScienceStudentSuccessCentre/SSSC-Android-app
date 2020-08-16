package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormFragment;

public class TermsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

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
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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

        termsViewModel = new ViewModelProvider(this, viewModelFactory).get(TermsViewModel.class);
        termsViewModel.state.observe(this, termViewState -> {
            if (termViewState.isLoading()) {
                System.out.println("Terms Loading");
            } else if (termViewState.isError()) {
                System.out.println("Terms ERROR: " + termViewState.getError());
            } else if (termViewState.isSuccess()) {
                if (termsViewModel.getTermItems().isEmpty()) {
                    emptyTermsMessage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    emptyTermsMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.setItems(termsViewModel.getTermItems());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

        termsViewModel.termSelected.observe(this, term -> {
            setHasOptionsMenu(false);
            ((MainActivity) requireActivity()).replaceFragment(CourseListFragment.newInstance(term.getTermId(), term.toString()));
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
        ((MainActivity) requireActivity()).replaceFragment(InputFormFragment.newInstance("", InputFormFragment.FormType.ADD_TERM.toString()));
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
