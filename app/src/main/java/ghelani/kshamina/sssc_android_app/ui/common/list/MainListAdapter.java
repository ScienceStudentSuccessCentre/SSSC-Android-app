package ghelani.kshamina.sssc_android_app.ui.common.list;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter;

import java.util.List;

import ghelani.kshamina.sssc_android_app.ui.common.adapterdelegates.TermsAdapterDelegate;

public class MainListAdapter extends ListDelegationAdapter<List<DiffItem>> {
    public MainListAdapter(Activity activity, List<DiffItem> items, ViewModel viewModel) {

        // DelegatesManager is a protected Field in ListDelegationAdapter
        delegatesManager.addDelegate(new TermsAdapterDelegate(activity, viewModel));

        // Set the items from super class.
        setItems(items);
    }
}
