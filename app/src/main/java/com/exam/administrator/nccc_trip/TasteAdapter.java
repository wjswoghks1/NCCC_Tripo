package com.exam.administrator.nccc_trip;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-17.
 */
public class TasteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<TasteItem> mItems;
    private int lastPosition = -1;

    public TasteAdapter(ArrayList items, Context mContext) {
        mItems = items;
        context = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 새로운 뷰를 만든다
        View v;
        RecyclerView.ViewHolder holder = null;
        if(viewType == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taste_best, parent, false);
            holder = new BestViewHolder(v);
            return holder;
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taste_item, parent, false);
            holder = new TasteViewHolder(v);
            return holder;
        }
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mItems.get(position).getImg() == null){

        }
        if(position == 0) {
            ((BestViewHolder)holder).textView.setText(mItems.get(position).getName());
            ((BestViewHolder)holder).imageView.setImageBitmap(mItems.get(position).getImg());
        }else if(position == 1){
            ((TasteViewHolder)holder).name.setText(mItems.get(position).getName());
            ((TasteViewHolder)holder).address.setText(mItems.get(position).getAddress());
            ((TasteViewHolder)holder).image.setImageBitmap(mItems.get(position).getImg());
            ((TasteViewHolder)holder).medal.setImageResource(R.drawable.first_medal);
        }else if(position == 2){
            ((TasteViewHolder)holder).name.setText(mItems.get(position).getName());
            ((TasteViewHolder)holder).address.setText(mItems.get(position).getAddress());
            ((TasteViewHolder)holder).image.setImageBitmap(mItems.get(position).getImg());
            ((TasteViewHolder)holder).medal.setImageResource(R.drawable.second_medal);
        }else{
            ((TasteViewHolder)holder).name.setText(mItems.get(position).getName());
            ((TasteViewHolder)holder).address.setText(mItems.get(position).getAddress());
            ((TasteViewHolder)holder).image.setImageBitmap(mItems.get(position).getImg());
        }
    }

    // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final static class BestViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public BestViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.best_taste_img);
            textView = (TextView) view.findViewById(R.id.best_taste_text);
        }
    }
    public final static class TasteViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView medal;
        public TextView address;
        public TextView name;
        public TextView distance;
        public TasteViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.taste_img);
            medal = (ImageView)view.findViewById(R.id.medal);
            address = (TextView) view.findViewById(R.id.taste_address);
            distance = (TextView) view.findViewById(R.id.taste_distance);
            name = (TextView) view.findViewById(R.id.taste_name);
        }
    }
}