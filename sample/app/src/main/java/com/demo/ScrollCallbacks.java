package com.demo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by root on 20/4/15.
 */
public abstract class ScrollCallbacks  extends RecyclerView.OnScrollListener {
    private static  int minScroll =30;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //Magic happens here
        super.onScrolled(recyclerView, dx, dy);
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        if (firstVisibleItem == 0) {
            //To avoid dummy first items visibility
            if(!controlsVisible) {
                onShow();
                controlsVisible = true;
            }
        } else {
            if (scrolledDistance > minScroll && controlsVisible) {
                onHide();
                controlsVisible = false;
                scrolledDistance = 0;
            } else if (scrolledDistance < -minScroll && !controlsVisible) {
                onShow();
                controlsVisible = true;
                scrolledDistance = 0;
            }
        }
        //Acccumulate the scroll difference
        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
            scrolledDistance += dy;
        }
    }

    public ScrollCallbacks setThreshold(int threshold){
        minScroll =threshold;
        return this;
    }
    public abstract void onHide();
    public abstract void onShow();

}
