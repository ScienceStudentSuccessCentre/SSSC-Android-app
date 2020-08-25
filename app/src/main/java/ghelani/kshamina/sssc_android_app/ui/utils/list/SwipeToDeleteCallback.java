package ghelani.kshamina.sssc_android_app.ui.utils.list;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.list.model.DiffItem;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {


    private Drawable icon;
    private ColorDrawable background = new ColorDrawable(Color.RED);
    private MainListAdapter adapter;
    private RecyclerView recyclerView;
    private EventListener.SwipeEventListener listener;
    private int minItemRange = -1, maxItemRange = -1;

    public SwipeToDeleteCallback(Context context, MainListAdapter adapter, RecyclerView recyclerView, EventListener.SwipeEventListener listener) {
        super(0, ItemTouchHelper.LEFT);
        icon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.listener = listener;
    }

    public SwipeToDeleteCallback(Context context, MainListAdapter adapter, RecyclerView recyclerView, EventListener.SwipeEventListener listener, int minItemRange, int maxItemRange) {
        this(context, adapter, recyclerView, listener);
        this.minItemRange = minItemRange;
        this.maxItemRange = maxItemRange;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        double backgroundCornerOffset = 20;

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop =
                itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();
        if (dX > 0) { // Swiping to the right
            int iconLeft = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            int iconRight = itemView.getLeft() + iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(
                    itemView.getLeft(), itemView.getTop(),
                    (int) (itemView.getLeft() + dX + backgroundCornerOffset),
                    itemView.getBottom()
            );
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(
                    (int) (itemView.getRight() + dX - backgroundCornerOffset),
                    itemView.getTop(), itemView.getRight(), itemView.getBottom()
            );
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        DiffItem item = adapter.getItems().get(position);

        listener.onItemSwiped(position);
        adapter.getItems().remove(position);
        adapter.notifyItemRemoved(position);

        Snackbar.make(recyclerView, "Deleted Item", Snackbar.LENGTH_LONG)
                .setAction("Undo", v -> {
                    adapter.getItems().add(position, item);
                    adapter.notifyItemInserted(position);
                }).show();
    }
}
