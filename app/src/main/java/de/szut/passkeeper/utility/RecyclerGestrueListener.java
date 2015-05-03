package de.szut.passkeeper.utility;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import de.szut.passkeeper.interfaces.IRecyclerItemClickListener;

/**
 * Created by redtiger on 03.05.15.
 */
public class RecyclerGestrueListener extends GestureDetector.SimpleOnGestureListener {
    private RecyclerViewAdapter.ViewHolder viewHolder;
    private IRecyclerItemClickListener iRecyclerItemClickListener;
    private int recyclerPosition;
    private Context context;

    public RecyclerGestrueListener(Context context, RecyclerViewAdapter.ViewHolder viewHolder, IRecyclerItemClickListener iRecyclerItemClickListener, int position) {
        this.viewHolder = viewHolder;
        this.iRecyclerItemClickListener = iRecyclerItemClickListener;
        this.recyclerPosition = position;
        this.context = context;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        iRecyclerItemClickListener.onRecyclerItemClick(recyclerPosition);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        int minSwipeDistance = viewConfiguration.getScaledPagingTouchSlop();
        if (e1.getX() - e2.getX() > minSwipeDistance) { // Right to left swipe
            int distance = (int) (e2.getX() - e1.getX());
            View animationView = viewHolder.mainView;
            View animationView1 = viewHolder.deleteAnimViewRight;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);
            animationView1.setVisibility(View.VISIBLE);
        } else if (e2.getX() - e1.getX() > minSwipeDistance) { // Left to right
            int distance = (int) (e1.getX() - e2.getX());
            View animationView = viewHolder.mainView;
            View animationView1 = viewHolder.deleteAnimViewRight;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = distance;
            params.leftMargin = -distance;
            animationView.setLayoutParams(params);
            animationView1.setVisibility(View.GONE);
        }
        return false;
    }
}
