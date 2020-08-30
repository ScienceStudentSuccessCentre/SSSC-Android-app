package ghelani.kshamina.sssc_android_app.ui.utils.events;

import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.ListItem;

public interface EventListener {
    interface FormInputItemListener {

        void onValueChanged(DiffItem item, String value);

    }
    interface ListItemEventListener {

        void onItemClicked(String id);

        boolean onItemLongClicked(int index);

        void deleteItem(int index);
    }

    interface SelectionItemEventListener {
        void onItemSelected(int index);
    }

    interface OpenItemEventListener {
        void onItemClicked();
    }

    interface SwipeEventListener{
        void onItemSwipedLeft(int index);
    }
}
