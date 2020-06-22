package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Terms");
        //requireActivity().setTitle("Terms");
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

                recyclerView.setAdapter(new MainListAdapter(getActivity(), termsViewModel.getTermItems(),termsViewModel));
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        termsViewModel.termSelected.observe(this, term -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            CourseListFragment courseListFragment = CourseListFragment.newInstance(term.getId(),term.getSeason() + " " +term.getYear());
            fragmentTransaction.replace(R.id.fragmentContainer, courseListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        termsViewModel.isDeleteMode.observe(this, isDeleteMode -> {

                termsViewModel.fetchTerms();

        });

        termsViewModel.fetchTerms();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.term_list_menu, menu);

        deleteItem = menu.findItem(R.id.deleteActionItem);
        cancelDeleteItem = menu.findItem(R.id.cancelDeleteItem);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.deleteActionItem:
                item.setVisible(false);
                cancelDeleteItem.setVisible(true);
                termsViewModel.isDeleteMode.setValue(true);

                return true;
            case R.id.cancelDeleteItem:
                item.setVisible(false);
                deleteItem.setVisible(true);
                termsViewModel.isDeleteMode.setValue(false);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddTermScreen() {
        ((MainActivity) requireActivity()).changeFragment(new AddTermFragment());
    }

    @Override
    public void onResume() {
        super.onResume();
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Terms");
    }
}
