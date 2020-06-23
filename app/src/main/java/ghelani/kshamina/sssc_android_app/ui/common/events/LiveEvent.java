package ghelani.kshamina.sssc_android_app.ui.common.events;

public class LiveEvent {
   private boolean toggleDeleteMode;
   private boolean deleteItem;
   private boolean addItem;
   private boolean itemClicked;
   private boolean itemLongClicked;

    public LiveEvent(boolean toggleDeleteMode, boolean deleteItem, boolean addItem, boolean itemClicked, boolean itemLongClicked) {
        this.toggleDeleteMode = toggleDeleteMode;
        this.deleteItem = deleteItem;
        this.addItem = addItem;
        this.itemClicked = itemClicked;
        this.itemLongClicked = itemLongClicked;
    }

    public boolean isToggleDeleteMode() {
        return toggleDeleteMode;
    }

    public void setToggleDeleteMode(boolean toggleDeleteMode) {
        this.toggleDeleteMode = toggleDeleteMode;
    }

    public boolean isDeleteItem() {
        return deleteItem;
    }

    public void setDeleteItem(boolean deleteItem) {
        this.deleteItem = deleteItem;
    }

    public boolean isAddItem() {
        return addItem;
    }

    public void setAddItem(boolean addItem) {
        this.addItem = addItem;
    }

    public boolean isItemClicked() {
        return itemClicked;
    }

    public void setItemClicked(boolean itemClicked) {
        this.itemClicked = itemClicked;
    }

    public boolean isItemLongClicked() {
        return itemLongClicked;
    }

    public void setItemLongClicked(boolean itemLongClicked) {
        this.itemLongClicked = itemLongClicked;
    }
}
