package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ghelani.kshamina.sssc_android_app.MainActivity;
import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.dagger.ViewModelFactory;
import ghelani.kshamina.sssc_android_app.ui.common.list.MainListAdapter;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term.AddTermFragment;
import ghelani.kshamina.sssc_android_app.ui.grades.terms.course_list.CourseListFragment;

public class TermsFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;

    private TermsViewModel termsViewModel;

    private RecyclerView recyclerView;

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
        addTermBtn.setOnClickListener(v -> {
            openAddTermScreen();
        });

        recyclerView = view.findViewById(R.id.termsRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        termsViewModel = new ViewModelProvider(this, viewModelFactory).get(TermsViewModel.class);
        termsViewModel.state.observe(this, termViewState -> {
            if (termViewState.isLoading()) {
                System.out.println("Terms Loading");
            } else if (termViewState.isError()) {
                System.out.println("Terms ERROR: " + termViewState.getError());
            } else if (termViewState.isSuccess()) {
                recyclerView.setAdapter(new MainListAdapter(getActivity(), termsViewModel.getTermItems()));
            }
        });

        termsViewModel.termSelected.observe(this, term -> {
            setHasOptionsMenu(false);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CourseListFragment courseListFragment = CourseListFragment.newInstance(term.getId(),term.toString());
            fragmentTransaction.replace(R.id.fragmentContainer, courseListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

        termsViewModel.fetchTerms();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.course_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.deleteActionItem) {
            if (termsViewModel.isDeleteMode()) {
                item.setIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_delete));
                termsViewModel.setDeleteMode(false);
            } else {
                item.setIcon(R.drawable.ic_close);
                termsViewModel.setDeleteMode(true);
            }
            termsViewModel.fetchTerms();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openAddTermScreen() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, new AddTermFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
