package ghelani.kshamina.sssc_android_app.ui.grades.terms.terms_list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ghelani.kshamina.sssc_android_app.model.Term;
import ghelani.kshamina.sssc_android_app.ui.common.list.ListViewModel;

public class TermsViewHolder extends RecyclerView.ViewHolder {
    public TermsViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    //ItemListBinding binding;
/*
    public TermsViewHolder(ItemListBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(Term term, ListViewModel viewModel) {
        binding.setViewmodel(viewModel);
        binding.setTerm(term);
        binding.executePendingBindings();
    }
    */

}
