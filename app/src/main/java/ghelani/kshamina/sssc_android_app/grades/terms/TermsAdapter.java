package ghelani.kshamina.sssc_android_app.grades.terms;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Term;


public class TermsAdapter extends RecyclerView.Adapter<TermsAdapter.MyViewHolder>{
    private List<Term> termsList;
    private View.OnClickListener onItemClickListener;

    public TermsAdapter(List<Term> terms) {
        this.termsList = terms;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_row, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Term term = termsList.get(position);
        holder.shortDate.setText(term.asShortString()); //TODO andrew left off here.. implement the holder class

        String lDate = term.asDisplayString();
        SpannableString dateSize = new SpannableString(lDate);
        dateSize.setSpan(new RelativeSizeSpan(1.4f), dateSize.length() - 2, dateSize.length(), 0);
        holder.longDate.setText(dateSize);
    }

    @Override
    public int getItemCount() {
        return termsList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView shortDate, longDate;

        public MyViewHolder(View view) {
            super(view);
            shortDate = view.findViewById(R.id.termShortDate);
            longDate = view.findViewById(R.id.termLongDate);

            view.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

}
