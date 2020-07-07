package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import androidx.recyclerview.widget.RecyclerView;

import ghelani.kshamina.sssc_android_app.databinding.ItemNewTermOptionBinding;

public class CheckListViewHolder extends RecyclerView.ViewHolder {
    private ItemNewTermOptionBinding binding;

    public CheckListViewHolder(ItemNewTermOptionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(String name, int visibility, int position, int type, AddTermContract.Presenter presenter) {
        binding.setName(name);
        binding.setPosition(position);
        binding.setVisibility(visibility);
        binding.setType(type);
        binding.setPresenter(presenter);
        binding.executePendingBindings();
    }
}
