package de.szut.passkeeper.utility;

import android.animation.ValueAnimator;
import android.content.Context;
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
        Log.d(RecyclerGestrueListener.class.getSimpleName(), "onScroll");
        int minSwipeDistance = 30;
        if (e1.getX() - e2.getX() > minSwipeDistance) { // Right to left swipe
            int distance = (int) (e2.getX() - e1.getX());
            View animationView = viewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);
        } else if (e2.getX() - e1.getX() > minSwipeDistance) { // Left to right
            int distance = (int) (e1.getX() - e2.getX());
            View animationView = viewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = distance;
            params.leftMargin = -distance;
            animationView.setLayoutParams(params);
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(RecyclerGestrueListener.class.getSimpleName(), "Fling");
        final int distanceX = (int) (e2.getX() - e1.getX());
        if (e1.getX() - e2.getX() > viewHolder.mainView.getWidth() / 2) { // Right to Left
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "right to left swipe more than the half");
        } else if (e2.getX() - e1.getX() > viewHolder.mainView.getWidth() / 2) { // Left to Right
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "left to right swipe more than the half");
        } else {
            Log.d(RecyclerGestrueListener.class.getSimpleName(), "small swipe " + Math.abs(distanceX));
            ValueAnimator animator = ValueAnimator.ofInt(Math.abs(distanceX), 0);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Log.d(RecyclerGestrueListener.class.getSimpleName(), "animated value " + animation.getAnimatedValue());
                    View animationView = viewHolder.mainView;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
                    params.rightMargin = distanceX > 0 ? -(int) animation.getAnimatedValue() : (int) animation.getAnimatedValue();
                    params.leftMargin = distanceX > 0 ? (int) animation.getAnimatedValue() : -(int) animation.getAnimatedValue();
                    animationView.setLayoutParams(params);
                }
            });
            animator.start();
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(RecyclerGestrueListener.class.getSimpleName(), "onDown");
        return true;
    }
}
