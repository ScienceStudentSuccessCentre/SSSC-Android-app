package ghelani.kshamina.sssc_android_app.ui.grades.terms;

import java.io.Serializable;

import ghelani.kshamina.sssc_android_app.ui.grades.terms.input_form.InputFormViewModel;

public abstract class SelectItemViewModel<T> extends InputFormViewModel implements Serializable {
   public abstract void setSelectedItem(T item);
}
