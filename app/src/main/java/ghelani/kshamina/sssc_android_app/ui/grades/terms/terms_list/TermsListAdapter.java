package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ghelani.kshamina.sssc_android_app.databinding.ItemTermBinding;
import ghelani.kshamina.sssc_android_app.entity.Term;

public class TermsListAdapter extends RecyclerView.Adapter<TermsViewHolder> {

    private List<Term> termList;
    private TermsViewModel viewModel;

    public TermsListAdapter(List<Term> termList, TermsViewModel viewModel) {
        this.termList = termList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public TermsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTermBinding binding = ItemTermBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new TermsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TermsViewHolder holder, int position) {
        Term term = termList.get(position);
        holder.onBind(term,viewModel);
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }
}
