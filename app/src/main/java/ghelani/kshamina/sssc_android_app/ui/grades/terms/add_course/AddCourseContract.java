package ghelani.kshamina.sssc_android_app.ui.grades.terms.add_course;

import java.util.List;

import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;

public interface AddCourseContract {
    interface View {
        void navigateToCoursesPage();

        void setCreateEnabled(boolean value);
    }

    interface Presenter {
        List<DiffItem> getInputItems();

        void setTermId(String termId);

        void onCreate();
    }
}
