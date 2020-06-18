package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_term;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ghelani.kshamina.sssc_android_app.databinding.ItemNewTermOptionBinding;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListViewHolder> {

    private List<String> optionList;
    private int selected;
    private int type;
    private AddTermContract.Presenter listener;

    public CheckListAdapter(List<String> optionList, int selected,int type, AddTermContract.Presenter listener) {
        this.optionList = optionList;
        this.selected = selected;
        this.type = type;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewTermOptionBinding binding = ItemNewTermOptionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new CheckListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListViewHolder holder, int position) {
        String name = optionList.get(position);
        holder.onBind(name,selected == position?View.VISIBLE:View.INVISIBLE, position,type, listener);
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }
}
