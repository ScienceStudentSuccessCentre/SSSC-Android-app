package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.SelectionItemEventListener;

public class SelectionItem implements DiffItem {

    private int index;
    private String value;
    private boolean isSelected;
    private SelectionItemEventListener clickListener;

    public SelectionItem(int index, String value, boolean isSelected, SelectionItemEventListener clickListener) {
        this.index = index;
        this.value = value;
        this.isSelected = isSelected;
        this.clickListener = clickListener;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public SelectionItemEventListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(SelectionItemEventListener clickListener) {
        this.clickListener = clickListener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
