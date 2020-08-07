package ghelani.kshamina.sssc_android_app.ui.common.events;

import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;

public interface EventListener {
    interface FormInputItemListener {

        void onValueChanged(DiffItem item, String value);

    }

    interface ListItemEventListener {

        void onItemClicked(String id);

        void deleteItem(String courseId);
    }

    interface SelectionItemEventListener {
        void onItemSelected(int index);
    }

    interface OpenItemEventListener {
        void onItemClicked();
    }
}
