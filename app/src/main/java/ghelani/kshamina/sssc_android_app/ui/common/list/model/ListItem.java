package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.ListItemEventListener;

/**
 * List view item to display Terms, Courses, and Assignments
 */
public class ListItem implements DiffItem {
    private String id;
    private String shortFormText;
    private String headerText;
    private String descriptionText;
    private boolean deleteIconVisible;
    private ListItemEventListener clickListener;

    public ListItem(String id, String shortFormText, String headerText, String descriptionText, boolean deleteIconVisible, ListItemEventListener clickListener) {
        this.id = id;
        this.shortFormText = shortFormText;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.deleteIconVisible = deleteIconVisible;
        this.clickListener = clickListener;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortFormText() {
        return shortFormText;
    }

    public void setShortFormText(String shortFormText) {
        this.shortFormText = shortFormText;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public boolean isDeleteIconVisible() {
        return deleteIconVisible;
    }

    public void setDeleteIconVisible(boolean deleteIconVisible) {
        this.deleteIconVisible = deleteIconVisible;
    }

    public ListItemEventListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ListItemEventListener clickListener) {
        this.clickListener = clickListener;
    }
}
