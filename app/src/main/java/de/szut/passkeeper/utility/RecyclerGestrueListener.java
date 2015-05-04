package de.szut.passkeeper.utility;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import de.szut.passkeeper.interfaces.IRecyclerItemClickListener;

/**
 * Created by redtiger on 03.05.15.
 */
public class RecyclerGestrueListener extends GestureDetector.SimpleOnGestureListener {
    private IRecyclerItemClickListener iRecyclerItemClickListener;
    private int recyclerPosition;
    private Context context;
    private RecyclerView view;
    private RecyclerViewAdapter.ViewHolder actualViewHolder;

    public RecyclerGestrueListener(Context context, IRecyclerItemClickListener iRecyclerItemClickListener, RecyclerView view) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener;
        this.context = context;
        this.view = view;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        iRecyclerItemClickListener.onRecyclerItemClick(recyclerPosition);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int minSwipeDistance = 30;
        if (e1.getX() - e2.getX() > minSwipeDistance) { // Right to left swipe
            int distance = (int) (e2.getX() - e1.getX());
            View animationView = actualViewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);
        } else if (e2.getX() - e1.getX() > minSwipeDistance) { // Left to right
            int distance = (int) (e1.getX() - e2.getX());
            View animationView = actualViewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = distance;
            params.leftMargin = -distance;
            animationView.setLayoutParams(params);
        }
        return false;
    }

    @Override
    public boolean onFling(final MotionEvent e1, final MotionEvent e2, float velocityX, float velocityY) {
        final int distanceX = (int) (e2.getX() - e1.getX());
        if (e1.getX() - e2.getX() > actualViewHolder.mainView.getWidth() / 2) { // Right to Left
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "right to left swipe more than the half");
        } else if (e2.getX() - e1.getX() > actualViewHolder.mainView.getWidth() / 2) { // Left to Right
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "left to right swipe more than the half");
        } else {
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "small swipe " + Math.abs(distanceX));
            ValueAnimator animator = ValueAnimator.ofInt(Math.abs(distanceX), 0);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d(RecyclerGestrueListener.class.getSimpleName(), "animated value " + animation.getAnimatedValue());
                    View animationView = actualViewHolder.mainView;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
                    params.rightMargin = distanceX > 0 ? -(int) animation.getAnimatedValue() : (int) animation.getAnimatedValue();
                    params.leftMargin = distanceX > 0 ? (int) animation.getAnimatedValue() : -(int) animation.getAnimatedValue();
                    animationView.setLayoutParams(params);
                }
            });
            animator.start();
        }
        actualViewHolder = null;
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null) {
            actualViewHolder = (RecyclerViewAdapter.ViewHolder) view.getChildViewHolder(childView);
        }
        return true;
    }
}
