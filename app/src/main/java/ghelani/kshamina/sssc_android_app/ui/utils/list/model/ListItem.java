package ghelani.kshamina.sssc_android_app.ui.utils.list.model;

import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;

/**
 * List view item to display Terms, Courses, and Assignments
 */
public class ListItem implements DiffItem {
    private String id;
    private String shortFormText;
    private String headerText;
    private String descriptionText;
    private boolean deleteIconVisible;
    private EventListener.ListItemEventListener clickListener;

    public ListItem(String id, String shortFormText, String headerText, String descriptionText, boolean deleteIconVisible, EventListener.ListItemEventListener clickListener) {
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

    public EventListener.ListItemEventListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(EventListener.ListItemEventListener clickListener) {
        this.clickListener = clickListener;
    }
}
