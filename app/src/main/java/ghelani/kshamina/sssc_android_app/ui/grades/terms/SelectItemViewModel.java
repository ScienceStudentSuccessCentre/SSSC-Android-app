package ghelani.kshamina.sssc_android_app.ui.grades.terms;

import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;

public abstract class SelectItemViewModel<T> extends InputFormViewModel {
   public abstract void setSelectedItem(T item);
}
