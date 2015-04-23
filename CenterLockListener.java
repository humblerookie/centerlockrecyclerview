package com.demo;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;

/**
 * Created by root on 24/4/15.
 */
public class CenterLockListener extends RecyclerView.OnScrollListener {
    boolean autoSet=true;//To avoid recursive calls
    int SCREEN_CENTER_X;
    public  CenterLockListener(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        SCREEN_CENTER_X =size.x/2;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        LinearLayoutManager lm= (LinearLayoutManager) recyclerView.getLayoutManager();
        if(!autoSet){
            if(newState==RecyclerView.SCROLL_STATE_IDLE){
                //ScrollStoppped
                View view=findCenterView(lm);//get the view nearest to center
                int scrollXNeeded= (int) (SCREEN_CENTER_X -(view.getLeft()+view.getRight())/2);//compute scroll from center
                recyclerView.smoothScrollBy(scrollXNeeded*(view.getRight()< SCREEN_CENTER_X ?1:-1),0);
                autoSet=true;
            }
        }
        if(newState==RecyclerView.SCROLL_STATE_DRAGGING || newState==RecyclerView.SCROLL_STATE_SETTLING){
            autoSet=false;
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }
    private View findCenterView(LinearLayoutManager lm) {
        int mindist=0;
        View view=null,retview=null;boolean notfound=true;
        for(int i=lm.findFirstVisibleItemPosition();i<=lm.findLastVisibleItemPosition()&& notfound;i++){
            view=lm.findViewByPosition(i);
            int diffleft=Math.abs(SCREEN_CENTER_X -view.getLeft());
            int diffright=Math.abs(SCREEN_CENTER_X -view.getRight());
            int leastdiff=diffleft>diffright?diffright:diffleft;

            if(leastdiff<=mindist || i==lm.findFirstVisibleItemPosition())
            {                mindist=leastdiff;
                retview=view;
            }
            else
            {   notfound=false;

            }
        }
        return retview;
    }
}
