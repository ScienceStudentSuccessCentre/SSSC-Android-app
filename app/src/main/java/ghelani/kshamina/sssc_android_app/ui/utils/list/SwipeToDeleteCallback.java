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

import ghelani.kshamina.sssc_android_app.R;
import ghelani.kshamina.sssc_android_app.ui.utils.events.EventListener;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.InputWeightFormAdapterDelegate;
import ghelani.kshamina.sssc_android_app.ui.utils.list.adapterdelegates.ListAdapterDelegate;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private Drawable icon;
    private ColorDrawable background = new ColorDrawable(Color.RED);
    private EventListener.SwipeEventListener listener;

    public SwipeToDeleteCallback(Context context, EventListener.SwipeEventListener listener) {
        super(0, ItemTouchHelper.LEFT);
        icon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        this.listener = listener;
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if(viewHolder instanceof InputWeightFormAdapterDelegate.InputWeightViewHolder || viewHolder instanceof ListAdapterDelegate.ListViewHolder) {
            return super.getSwipeDirs(recyclerView, viewHolder);
        }else{
            return 0;
        }
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

        listener.onItemSwiped(position);
    }
}
