package com.example.marina.openweather.screens.main.listener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.marina.openweather.R;

public class DeleteMoveItemTouchHelper extends ItemTouchHelper.Callback {
    private TouchCallback touchCallback;
    private Bitmap icon;
    private Drawable background;
    private Paint paint;
    private boolean isInitiated;

    public DeleteMoveItemTouchHelper(TouchCallback touchCallback) {
        this.touchCallback = touchCallback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getAdapterPosition() == 0 || target.getAdapterPosition() == 0) {
            return false;
        }
        touchCallback.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder.getAdapterPosition() == 0) {
            return;
        }
        touchCallback.onDismiss(viewHolder.getAdapterPosition());
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final View itemView = viewHolder.itemView;
            if (viewHolder.getAdapterPosition() == -1 || viewHolder.getAdapterPosition() == 0) {
                return;
            }

            if (!isInitiated) {
                init(recyclerView.getContext());
            }

            final int itemHeight = itemView.getBottom() - itemView.getTop();
            final int itemWidth = itemView.getRight() - itemView.getLeft();

            background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
            background.draw(c);

            final int imageX = itemView.getLeft() + (int) dX / 2 - icon.getWidth() / 2;
            final int imageY = itemHeight / 2 + itemView.getTop() - icon.getHeight() / 2;

            c.drawBitmap(icon, imageX, imageY, paint);

            final float translationX = Math.min(-dX, itemWidth / 2);
            viewHolder.itemView.setTranslationX(-translationX);
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    private void init(Context context) {
        background = new ColorDrawable(ContextCompat.getColor(context, R.color.red));
        icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.trash_bin);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        isInitiated = true;
    }
}
