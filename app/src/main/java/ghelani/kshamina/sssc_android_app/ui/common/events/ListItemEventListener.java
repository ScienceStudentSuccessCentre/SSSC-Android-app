package ghelani.kshamina.sssc_android_app.ui.common.events;

public interface ListItemEventListener {

    void onItemClicked(String id);
    boolean onItemLongClicked(String id);
    void toggleDeleteMode();
    void deleteItem(String courseId);
}
