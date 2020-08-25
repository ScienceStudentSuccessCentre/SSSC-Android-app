package ghelani.kshamina.sssc_android_app.ui.utils.list;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter;

import java.util.List;

import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.InputFormAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.InputWeightFormAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.ListAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.SelectionInputAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.TextFormAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.MentorAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;

/**
 * List adapter will contain references to AdapterDelegates
 * Each AdapterDelegate will generate a list specific to the  type
 * of DiffItem it contains.
 */
public class MainListAdapter extends ListDelegationAdapter<List<DiffItem>> {
    public MainListAdapter(Activity activity, List<DiffItem> items) {

        // DelegatesManager is a protected Field in ListDelegationAdapter
        delegatesManager
                .addDelegate(new ListAdapterDelegate(activity))
                .addDelegate(new InputFormAdapterDelegate(activity))
                .addDelegate(new TextFormAdapterDelegate(activity))
                .addDelegate(new InputWeightFormAdapterDelegate(activity))
                .addDelegate(new SelectionInputAdapterDelegate(activity))
                .addDelegate(new MentorAdapterDelegate(activity));

        // Set the items from super class.
        setItems(items);
    }
}
