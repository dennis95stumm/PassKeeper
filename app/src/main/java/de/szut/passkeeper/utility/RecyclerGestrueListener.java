package de.szut.passkeeper.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import de.szut.passkeeper.R;
import de.szut.passkeeper.interfaces.IRecyclerActivity;

/**
 *
 */
public class RecyclerGestrueListener extends GestureDetector.SimpleOnGestureListener {
    private IRecyclerActivity iRecyclerActivity;
    private int recyclerPosition = -1;
    private RecyclerView view;
    private RecyclerViewAdapter.ViewHolder actualViewHolder;
    private boolean swipingEnabled = true;
    private int MIN_FLING_VELOCITY;
    private int MAX_FLING_VELOCITY;
    private int TOUCH_SLOP;
    private int selectedItem = -1;

    /**
     * @param iRecyclerActivity
     * @param view
     */
    public RecyclerGestrueListener(IRecyclerActivity iRecyclerActivity, RecyclerView view) {
        this.iRecyclerActivity = iRecyclerActivity;
        this.view = view;
        ViewConfiguration vc = ViewConfiguration.get(iRecyclerActivity);
        MAX_FLING_VELOCITY = vc.getScaledMaximumFlingVelocity();
        MIN_FLING_VELOCITY = vc.getScaledMinimumFlingVelocity();
        TOUCH_SLOP = vc.getScaledTouchSlop();
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (actualViewHolder == null || !swipingEnabled) {
            return false;
        }
        swipingEnabled = true;
        iRecyclerActivity.onRecyclerItemClick(recyclerPosition);
        actualViewHolder.mainView.setPressed(true);
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (actualViewHolder == null || !swipingEnabled || Math.abs(e2.getY() - e1.getY()) > TOUCH_SLOP) {
            return false;
        }
        if (e1.getX() - e2.getX() > TOUCH_SLOP) { // Right to left swipe
            int distance = (int) (e2.getX() - e1.getX());
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_left).setVisibility(View.GONE);
            actualViewHolder.deleteAnimView.findViewById(R.id.delete_image_right).setVisibility(View.VISIBLE);
            View animationView = actualViewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);
        } else if (e2.getX() - e1.getX() > TOUCH_SLOP) { // Left to right
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
        if (actualViewHolder == null || !swipingEnabled || MIN_FLING_VELOCITY > Math.abs(velocityX) || Math.abs(velocityX) > MAX_FLING_VELOCITY || velocityY > Math.abs(velocityX)) {
            if (MIN_FLING_VELOCITY > Math.abs(velocityX) || Math.abs(velocityX) > MAX_FLING_VELOCITY || velocityY > Math.abs(velocityX)) {
                resetView();
            }
            return false;
        }
        swipingEnabled = false;
        if (e1.getX() - e2.getX() > actualViewHolder.mainView.getWidth() * 0.2) {
            swipe(Math.abs(distanceX), true);
        } else if (e2.getX() - e1.getX() > actualViewHolder.mainView.getWidth() * 0.2) {
            swipe(Math.abs(distanceX), false);
        } else {
            ValueAnimator animator = ValueAnimator.ofInt(Math.abs(distanceX), 0);
            animator.setDuration(250);
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

    /**
     *
     * @param distanceX
     * @param rightToLeft
     */
    private void swipe(int distanceX, final boolean rightToLeft) {
        ValueAnimator animator = ValueAnimator.ofInt(distanceX, actualViewHolder.mainView.getWidth());
        animator.setDuration(250);
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
                    InputMethodManager imm = (InputMethodManager) iRecyclerActivity.getSystemService(
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
                                InputMethodManager imm = (InputMethodManager) iRecyclerActivity.getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(iRecyclerActivity.getWindow().getDecorView().getWindowToken(), 0);
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

    /**
     *
     */
    private void resetView() {
        if (actualViewHolder != null) {
            actualViewHolder.delteConfirmation.setVisibility(View.GONE);
            actualViewHolder.deleteAnimView.setVisibility(View.VISIBLE);
            View animationView = actualViewHolder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = 0;
            params.leftMargin = 0;
            animationView.setLayoutParams(params);
            if (actualViewHolder.delteConfirmationView instanceof EditText) {
                ((EditText) actualViewHolder.delteConfirmationView).setText(null);
                InputMethodManager imm = (InputMethodManager) iRecyclerActivity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(iRecyclerActivity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }
        actualViewHolder = null;
        swipingEnabled = true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        if (swipingEnabled && actualViewHolder != null && iRecyclerActivity.longPressEnabled()) {
            actualViewHolder.mainView.setPressed(false);
            if (selectedItem != recyclerPosition) {
                if (selectedItem != -1) {
                    RecyclerViewAdapter.ViewHolder viewHolder = (RecyclerViewAdapter.ViewHolder) view.getChildViewHolder(view.getChildAt(selectedItem));
                    viewHolder.mainView.setSelected(false);
                }
                actualViewHolder.mainView.setSelected(true);
                selectedItem = recyclerPosition;
                iRecyclerActivity.getEditMenu().setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        actualViewHolder.mainView.setSelected(false);
                        actualViewHolder = null;
                        recyclerPosition = -1;
                        swipingEnabled = true;
                        iRecyclerActivity.getEditMenu().setVisible(false);
                        boolean returnVal = iRecyclerActivity.editItem(selectedItem);
                        selectedItem = -1;
                        return returnVal;
                    }
                });
                iRecyclerActivity.getEditMenu().setVisible(true);
            } else {
                actualViewHolder.mainView.setSelected(false);
                iRecyclerActivity.getEditMenu().setVisible(false);
                selectedItem = -1;
            }
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {
        if (swipingEnabled && actualViewHolder != null && iRecyclerActivity.longPressEnabled())
            actualViewHolder.mainView.setPressed(true);
    }
}
