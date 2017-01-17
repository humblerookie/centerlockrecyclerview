package com.demo;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class CenterLockListener extends RecyclerView.OnScrollListener {

    //Flag to avoid recursive calls
    private boolean isCascadedCall = true;

    //The pivot to be snapped to
    private int centerPivot;


    public CenterLockListener(int centerPivot) {
        this.centerPivot = centerPivot;
    }


    /**
     * Overrides the scroll state changes and
     * repositions the scroll to lock the view
     * to the center
     *
     * @param recyclerView
     * @param newState
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();

        // Default pivot , Its a bit inaccurate .
        // Better pass the center pivot as your Center Indicator view's
        // calculated center on it OnGlobalLayoutListener event

        if (centerPivot == 0) {
            centerPivot = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (recyclerView.getLeft() + recyclerView.getRight()) : (recyclerView.getTop() + recyclerView.getBottom());
        }

        if (!isCascadedCall) {

            //Scroll has stopped we can now proceed with computations
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                View view = findCenterView(lm);//get the view nearest to center
                int viewCenter = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;

                // Compute scroll from center
                // Add or subtract any offsets you need here
                int scrollNeeded = viewCenter - centerPivot;

                if (lm.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    recyclerView.smoothScrollBy(scrollNeeded, 0);
                } else {
                    recyclerView.smoothScrollBy(0, scrollNeeded);
                }

                isCascadedCall = true;
            }
        }

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
            isCascadedCall = false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /**
     * This method iterates through the visible views and
     * figures out the
     *
     * @param lm - The LinearLayoutManager of the RecyclerView
     * @return View nearest to the center
     */
    private View findCenterView(LinearLayoutManager lm) {
        View view = null;
        View returnView = null;

        int minDistance = 0;
        boolean isNotFound = true;

        for (int i = lm.findFirstVisibleItemPosition(); i <= lm.findLastVisibleItemPosition() && isNotFound; i++) {

            view = lm.findViewByPosition(i);

            int center = lm.getOrientation() == LinearLayoutManager.HORIZONTAL ? (view.getLeft() + view.getRight()) / 2 : (view.getTop() + view.getBottom()) / 2;
            int leastDifference = Math.abs(centerPivot - center);

            if (leastDifference <= minDistance || i == lm.findFirstVisibleItemPosition()) {
                minDistance = leastDifference;
                returnView = view;
            } else {
                isNotFound = false;

            }
        }
        return returnView;
    }


}
