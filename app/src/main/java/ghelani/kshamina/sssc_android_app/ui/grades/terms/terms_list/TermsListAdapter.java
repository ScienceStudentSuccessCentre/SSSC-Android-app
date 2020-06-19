package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.ui.common.list.ListViewModel;

public class TermsListAdapter extends RecyclerView.Adapter<TermsViewHolder> {

    private List<Term> termList;
    private ListViewModel viewModel;

    public TermsListAdapter(List<Term> termList, ListViewModel viewModel) {
        this.termList = termList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ItemListBinding binding = ItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        //return new TermsViewHolder(binding);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, int position) {
        Term term = termList.get(position);
       //holder.onBind(term,viewModel);
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }
}
