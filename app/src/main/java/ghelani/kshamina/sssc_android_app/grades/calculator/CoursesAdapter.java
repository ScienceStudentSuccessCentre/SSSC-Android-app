package ghelani.kshamina.sssc_android_app.grades.calculator;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.entity.Course;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.MyViewHolder> {
    private List<Course> coursesList;
    private View.OnClickListener onItemClickListener;

    public CoursesAdapter(List<Course> courses) {
        this.coursesList = courses;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Course course = coursesList.get(position);

        holder.courseRowCode.setText(String.format(Locale.CANADA, "[%s] %s",
                course.courseTermId, course.courseCode));
        holder.courseRowName.setText(course.courseName);
        holder.letterGrade.setText(course.courseFinalGrade);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        onItemClickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View colouredBar;
        private int[] colours = new int[] { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };

        public TextView courseRowCode, courseRowName, letterGrade;

        public MyViewHolder(View view) {
            super(view);
            colouredBar = view.findViewById(R.id.courseRowLeftBorder);
            courseRowCode = view.findViewById(R.id.courseRowCode);
            courseRowName = view.findViewById(R.id.courseRowName);
            letterGrade = view.findViewById(R.id.courseRowGrade);

            int randomColour = colours[(int)(colours.length * Math.random())];
            colouredBar.setBackgroundColor(randomColour);

            view.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }

}
