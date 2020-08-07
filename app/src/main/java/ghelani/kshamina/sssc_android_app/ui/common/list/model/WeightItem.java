package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.EventListener;

public class WeightItem implements DiffItem {
    private int index;
    private String name;
    private String value;
    private EventListener.FormInputItemListener nameListener;
    private EventListener.FormInputItemListener valueListener;

    public WeightItem(int index, String name, String value, EventListener.FormInputItemListener nameListener, EventListener.FormInputItemListener valueListener) {
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

    public EventListener.FormInputItemListener getNameListener() {
        return nameListener;
    }

    public void setNameListener(EventListener.FormInputItemListener nameListener) {
        this.nameListener = nameListener;
    }

    public EventListener.FormInputItemListener getValueListener() {
        return valueListener;
    }

    public void setValueListener(EventListener.FormInputItemListener valueListener) {
        this.valueListener = valueListener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
