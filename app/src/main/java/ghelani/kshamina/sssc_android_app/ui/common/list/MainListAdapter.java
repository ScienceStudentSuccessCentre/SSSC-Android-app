package ghelani.kshamina.sssc_android_app.ui.common.list;

import android.app.Activity;

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter;

import java.util.List;

import ghelani.kshamina.sssc_android_app.ui.common.list.adapterdelegates.ListAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.common.list.model.DiffItem;

/**
 * List adapter will contain references to AdapterDelegates
 * Each AdapterDelegate will generate a list specific to the  type
 * of DiffItem it contains.
 */
public class MainListAdapter extends ListDelegationAdapter<List<DiffItem>> {
    public MainListAdapter(Activity activity, List<DiffItem> items) {

        // DelegatesManager is a protected Field in ListDelegationAdapter
        delegatesManager
                .addDelegate(new ListAdapterDelegate(activity));
                //.addDelegate(new TermsAdapterDelegate(activity));
                //.addDelegate(new CourseAdapterDelegate(activity, viewModel));

        // Set the items from super class.
        setItems(items);
    }
}
