package ghelani.kshamina.sssc_android_app.ui.common.list.model;

import ghelani.kshamina.sssc_android_app.ui.common.events.ItemClickListener;

/**
 * List view item to display Terms, Courses, and Assignments
 */
public class ListItem implements DiffItem {
    private String id;
    private String shortFormText;
    private String headerText;
    private String descriptionText;
    private int deleteIconVisibilty;
    private ItemClickListener clickListener;

    public ListItem(String id, String shortFormText, String headerText, String descriptionText, int deleteIconVisibilty, ItemClickListener clickListener) {
        this.id = id;
        this.shortFormText = shortFormText;
        this.headerText = headerText;
        this.descriptionText = descriptionText;
        this.deleteIconVisibilty = deleteIconVisibilty;
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

    public int getDeleteIconVisibilty() {
        return deleteIconVisibilty;
    }

    public void setDeleteIconVisibilty(int deleteIconVisibilty) {
        this.deleteIconVisibilty = deleteIconVisibilty;
    }

    public ItemClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
