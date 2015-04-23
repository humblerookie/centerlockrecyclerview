package com.demo;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 21/4/15.
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SampleBean> mData;
    private static final int ITEM_SWIPE = 1;
    private static final int ITEM_NORMAL = 0;
    private final int ITEM_WIDTH;
    private final int CENTERX;
    private ReviewAdapter adapter;
    Context context;
    public MainAdapter(  ArrayList<SampleBean> mDat,Context context,int maxwidth ){
        mData=mDat;
        this.context=context;
        Display display = ((MainActivity)context).getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        CENTERX=size.x/2;
        ITEM_WIDTH=(int) context.getResources().getDimension(R.dimen.flexible_space_image_height);
        adapter=new ReviewAdapter(mData,ITEM_WIDTH,maxwidth-(int) context.getResources().getDimension(R.dimen.flexible_space_image_height));
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        if (viewType == ITEM_NORMAL) {

            final View view = LayoutInflater.from(context).inflate(R.layout.item_normal, parent, false);

            return new NormalHolder(view);
        } else if (viewType == ITEM_SWIPE) {
            final View view = LayoutInflater.from(context).inflate(R.layout.item_horizontal, parent, false);
            SwipeHolder holder=new SwipeHolder(view);
            LinearLayoutManager lm=new LinearLayoutManager(context);
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.rc.setLayoutManager(lm);
            return holder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof NormalHolder){
            ((NormalHolder) holder).tv.setText(mData.get(position).getTitle());
            if(position==0)
            {
                ((NormalHolder) holder).wrapper.getLayoutParams().height= (int) ((NormalHolder) holder).wrapper.getContext().getResources().getDimension(R.dimen.toolbarheight);
                ((NormalHolder) holder).wrapper.setVisibility(View.INVISIBLE);
            }
            else{
                ((NormalHolder) holder).wrapper.getLayoutParams().height= (int) ((NormalHolder) holder).wrapper.getContext().getResources().getDimension(R.dimen.flexible_space_show_fab_offset);
                ((NormalHolder) holder).wrapper.setVisibility(View.VISIBLE);
            }
        }
        else{
            ((SwipeHolder)holder).rc.setAdapter(adapter);
            ((SwipeHolder)holder).rc.setOnScrollListener(new CenterLockListener((android.app.Activity) context));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
             return mData.get(position).isNormal()?ITEM_NORMAL:ITEM_SWIPE;


    }
    public class SwipeHolder extends RecyclerView.ViewHolder {
        RecyclerView rc;
        public SwipeHolder(View itemView) {
            super(itemView);
            rc= (RecyclerView) itemView.findViewById(R.id.horizontalList);
        }
    }
    public class NormalHolder extends RecyclerView.ViewHolder {
        TextView tv;
        View wrapper;
        public NormalHolder(View itemView) {
            super(itemView);
            wrapper=itemView.findViewById(R.id.wrapper);
            tv= (TextView) itemView.findViewById(R.id.text);
        }
    }


    private View findCenterView(LinearLayoutManager lm) {
        int mindist=0;
        View view=null,retview=null;boolean notfound=true;
        for(int i=lm.findFirstVisibleItemPosition();i<=lm.findLastVisibleItemPosition()&& notfound;i++){
            view=lm.findViewByPosition(i);
            int diffleft=Math.abs(CENTERX-view.getLeft());
            int diffright=Math.abs(CENTERX-view.getRight());
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
