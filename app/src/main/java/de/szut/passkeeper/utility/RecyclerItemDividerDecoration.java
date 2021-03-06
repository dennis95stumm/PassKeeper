package de.szut.passkeeper.utility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import de.szut.passkeeper.R;

/**
 * Divider decoration for the recycler items.
 */
public class RecyclerItemDividerDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    /**
     * Constructor.
     * @param context Context to get the divider drawable
     */
    public RecyclerItemDividerDecoration(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.recycler_item_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
