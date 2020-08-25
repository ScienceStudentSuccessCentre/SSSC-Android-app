package ghelani.kshamina.sssc_android_app.ui.grades.calculator;

import android.content.Context;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.database.GradesDatabase;
import ghelani.kshamina.sssc_android_app.database.TermDao;
import ghelani.kshamina.sssc_android_app.entity.CourseEntity;
import ghelani.kshamina.sssc_android_app.entity.TermEntity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {

    private List<CourseEntity> courseEntity;
    private Context activityContext;
    private View.OnClickListener onItemClickListener;
    private TermDao termDao;

    private static int colourIndex = 0;  // To scroll through colours

    public CoursesAdapter(List<CourseEntity> courseEntity, Context activityContext, GradesDatabase db) {
        this.courseEntity = courseEntity;
        this.activityContext = activityContext;
        this.onItemClickListener = null;
        this.termDao = db.getTermDao();
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final CourseEntity courseEntity = this.courseEntity.get(position);

        // Fill in TextFields (leaving term as ...)
        holder.courseRowCode.setText(String.format(Locale.CANADA, "[...] %s", courseEntity.courseCode));
        holder.courseRowName.setText(courseEntity.courseName);
        holder.letterGrade.setText(courseEntity.courseFinalGrade == null || courseEntity.courseFinalGrade.isEmpty() ? "N/A" : courseEntity.courseFinalGrade);

        // Retrieve term from database
        termDao.getTermById(courseEntity.courseTermId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<TermEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(TermEntity termEntity) {
                        holder.courseRowCode.setText(String.format(Locale.CANADA, "[%s] %s",
                                termEntity.asShortString(), courseEntity.courseCode));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return courseEntity.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View colouredBar;
        private int[] colours = new int[]{
                Color.parseColor("#AB47BB"),  // Purple
                Color.parseColor("#EF6D40"),  // Orange
                Color.parseColor("#ED4134"),  // Red
                Color.parseColor("#50AF50"),  // Green
                Color.parseColor("#3096F3"),  // Light blue
                Color.parseColor("#F8C045"),  // Yellow
                Color.parseColor("#47A69B"),  // Turquoise
        };

        public TextView courseRowCode, courseRowName, letterGrade;

        public MyViewHolder(View view) {
            super(view);
            colouredBar = view.findViewById(R.id.courseRowLeftBorder);
            courseRowCode = view.findViewById(R.id.courseRowCode);
            courseRowName = view.findViewById(R.id.courseRowName);
            letterGrade = view.findViewById(R.id.courseRowGrade);

            colouredBar.setBackgroundColor(colours[colourIndex]);
            colourIndex = (colourIndex + 1) % colours.length;

            view.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

}
