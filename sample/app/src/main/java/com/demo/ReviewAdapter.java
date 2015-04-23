package com.demo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 16/4/15.
 */
public class ReviewAdapter extends  RecyclerView.Adapter<ReviewAdapter.ReviewHolder>  {
    ArrayList<SampleBean> mData;
    int totalpadding;
    int itemwidth;
    public ReviewAdapter(ArrayList<SampleBean> data,int totalpadding,int itemwidth){
        mData=data;
        this.totalpadding=totalpadding;
        this.itemwidth=itemwidth;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.item_horz,parent,false);
        v.getLayoutParams().width= itemwidth;
        return new ReviewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.container.setPadding(0,0,0,0);
//Add intial item and final item  padding
        if(position==0){

            if(mData.size()==1){
                holder.container.setPadding(totalpadding/2,0,totalpadding/2,0);
            }
            else{
                holder.container.setPadding(totalpadding,0,0,0);
            }
        }
        else
        if(position==mData.size()-1){
            holder.container.setPadding(0,0,totalpadding,0);
        }
        holder.tvName.setText(mData.get(position).getTitle());
        holder.tvName.setGravity(Gravity.CENTER);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        protected TextView tvName;
        View container;

        public ReviewHolder(View itemView) {
            super(itemView);
            container=itemView;
            tvName= (TextView) itemView.findViewById(R.id.text);
        }
    }

}
