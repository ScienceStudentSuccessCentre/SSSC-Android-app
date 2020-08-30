package ghelani.kshamina.sssc_android_app.ui.utils.list.model;

import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;

public class MentorItem implements DiffItem {
    private String imageUrl;
    private String header;
    private String description;
    private EventListener.OpenItemEventListener clickListener;

    public MentorItem(String imageUrl, String header, String description, EventListener.OpenItemEventListener clickListener) {
        this.imageUrl = imageUrl;
        this.header = header;
        this.description = description;
        this.clickListener = clickListener;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventListener.OpenItemEventListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(EventListener.OpenItemEventListener clickListener) {
        this.clickListener = clickListener;
    }
}
