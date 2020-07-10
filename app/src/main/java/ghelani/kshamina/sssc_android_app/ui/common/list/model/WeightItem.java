package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.FormInputItemListener;

public class WeightItem implements DiffItem {
    private int index;
    private String name;
    private String value;
    private FormInputItemListener nameListener;
    private FormInputItemListener valueListener;

    public WeightItem(int index, String name, String value, FormInputItemListener nameListener, FormInputItemListener valueListener) {
        this.index = index;
        this.name = name;
        this.value = value;
        this.nameListener = nameListener;
        this.valueListener = valueListener;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FormInputItemListener getNameListener() {
        return nameListener;
    }

    public void setNameListener(FormInputItemListener nameListener) {
        this.nameListener = nameListener;
    }

    public FormInputItemListener getValueListener() {
        return valueListener;
    }

    public void setValueListener(FormInputItemListener valueListener) {
        this.valueListener = valueListener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
