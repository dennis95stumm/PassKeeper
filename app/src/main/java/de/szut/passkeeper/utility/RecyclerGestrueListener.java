package de.szut.passkeeper.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IRecyclerActivity;

/**
 * Created by redtiger on 03.05.15.
 */
public class RecyclerGestrueListener extends GestureDetector.SimpleOnGestureListener {
    private IRecyclerActivity iRecyclerActivity;
    private int recyclerPosition;
    private Context context;
    private RecyclerView view;
    private RecyclerViewAdapter.ViewHolder actualViewHolder;
    private boolean swipingEnabled = true;
    private int MIN_SWIPE_DISTANCE_X = 100;
    private int MIN_SWIPE_DISTANCE_Y = 30;

    public RecyclerGestrueListener(Context context, IRecyclerActivity iRecyclerActivity, RecyclerView view) {
        this.iRecyclerActivity = iRecyclerActivity;
        this.context = context;
        this.view = view;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (actualViewHolder == null || !swipingEnabled) {
            return false;
        }
        swipingEnabled = true;
        iRecyclerActivity.onRecyclerItemClick(recyclerPosition);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        // || distanceY > MIN_SWIPE_DISTANCE_Y
        if (actualViewHolder == null || !swipingEnabled) {
            return false;
        }
        if (e1.getX() - e2.getX() > MIN_SWIPE_DISTANCE_X) { // Right to left swipe
            int distance = (int) (e2.getX() - e1.getX());
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_left).setVisibility(View.GONE);
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_right).setVisibility(View.VISIBLE);
            View animationView = actualViewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);
        } else if (e2.getX() - e1.getX() > MIN_SWIPE_DISTANCE_X) { // Left to right
            int distance = (int) (e1.getX() - e2.getX());
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_left).setVisibility(View.VISIBLE);
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_right).setVisibility(View.GONE);
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
        if (actualViewHolder == null || !swipingEnabled) {// || Math.abs(e2.getY()-e1.getY()) > MIN_SWIPE_DISTANCE_Y
            return false;
        }
        swipingEnabled = false;
        if (e1.getX() - e2.getX() > actualViewHolder.mainView.getWidth() * 0.80) { // Right to Left
            swipe(Math.abs(distanceX), true);
        } else if (e2.getX() - e1.getX() > actualViewHolder.mainView.getWidth() * 0.80) { // Left to Right
            swipe(Math.abs(distanceX), false);
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(Math.abs(distanceX), 0);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    View animationView = actualViewHolder.mainView;
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
                    params.rightMargin = distanceX > 0 ? -(int) animation.getAnimatedValue() : (int) animation.getAnimatedValue();
                    params.leftMargin = distanceX > 0 ? (int) animation.getAnimatedValue() : -(int) animation.getAnimatedValue();
                    animationView.setLayoutParams(params);
                }
            });
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    actualViewHolder = null;
                    swipingEnabled = true;
                }
            });
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (swipingEnabled) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                actualViewHolder = (RecyclerViewAdapter.ViewHolder) view.getChildViewHolder(childView);
                recyclerPosition = view.getChildAdapterPosition(childView);
            }

            return true;
        }
        return false;
    }

    private void swipe(int distanceX, final boolean rightToLeft) {
        ValueAnimator animator = ValueAnimator.ofInt(distanceX, actualViewHolder.mainView.getWidth());
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                View animationView = actualViewHolder.mainView;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
                params.rightMargin = (int) animation.getAnimatedValue() * (rightToLeft ? 1 : -1);
                params.leftMargin = (int) animation.getAnimatedValue() * (rightToLeft ? -1 : 1);
                animationView.setLayoutParams(params);
            }
        });
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                actualViewHolder.deleteAnimView.setVisibility(View.GONE);
                actualViewHolder.delteConfirmation.setVisibility(View.VISIBLE);
                if (actualViewHolder.delteConfirmationView instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    actualViewHolder.delteConfirmationView.requestFocus();
                }
                actualViewHolder.delteConfirmation.findViewById(R.id.deltition_yes).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (actualViewHolder != null) {

                            if (iRecyclerActivity.confirmRemove(actualViewHolder.delteConfirmationView instanceof EditText ? ((EditText) actualViewHolder.delteConfirmationView).getText().toString() : null, recyclerPosition)) {
                                iRecyclerActivity.removeItem(recyclerPosition);
                                actualViewHolder = null;
                                swipingEnabled = true;
                            } else {
                                resetView();
                                iRecyclerActivity.onRemoveConfirmationFailed();
                            }
                        }
                        return true;
                    }
                });
                actualViewHolder.delteConfirmation.findViewById(R.id.deltition_no).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (actualViewHolder != null) {
                            resetView();
                        }
                        return true;
                    }
                });
            }
        });
    }

    private void resetView() {
        actualViewHolder.delteConfirmation.setVisibility(View.GONE);
        actualViewHolder.deleteAnimView.setVisibility(View.VISIBLE);
        View animationView = actualViewHolder.mainView;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        animationView.setLayoutParams(params);
        if (actualViewHolder.delteConfirmationView instanceof EditText) {
            ((EditText) actualViewHolder.delteConfirmationView).setText(null);
            InputMethodManager imm = (InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(actualViewHolder.delteConfirmationView.getWindowToken(), 0);
        }
        actualViewHolder = null;
        swipingEnabled = true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
        // TODO longpress
        // makierungs icon
        // nur eine auswahl möglich
        // bei auswahl bearbeitungszeichen im floating button
        // beim betätigen des baerbeitungsbuttons entsprechende funktion aufrufen
    }
}
