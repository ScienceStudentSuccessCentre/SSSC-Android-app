package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import androidx.recyclerview.widget.RecyclerView;

import ghelani.kshamina.sssc_android_app.databinding.ItemTermBinding;
import ghelani.kshamina.sssc_android_app.entity.Term;

public class TermsViewHolder extends RecyclerView.ViewHolder {
    ItemTermBinding binding;

    public TermsViewHolder(ItemTermBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(Term term, TermsViewModel viewModel) {
        binding.setViewmodel(viewModel);
        binding.setTerm(term );
        binding.executePendingBindings();
    }
}
