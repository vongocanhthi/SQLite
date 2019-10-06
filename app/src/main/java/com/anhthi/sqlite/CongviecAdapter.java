package com.anhthi.sqlite;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CongviecAdapter extends BaseAdapter {
    MainActivity context;
    ArrayList<Congviec> congviecArrayList;

    public CongviecAdapter(MainActivity context, ArrayList<Congviec> congviecArrayList) {
        this.context = context;
        this.congviecArrayList = congviecArrayList;
    }

    @Override
    public int getCount() {
        return congviecArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        TextView txtTenCV, txtLike;
        ImageView imgDelete, imgEdit, imgLike;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.row_congviec, null);

        final ViewHolder holder = new ViewHolder();
        holder.txtTenCV = convertView.findViewById(R.id.txtTenCV);
        holder.imgDelete = convertView.findViewById(R.id.imgDelete);
        holder.txtLike = convertView.findViewById(R.id.txtLike);
        holder.imgEdit = convertView.findViewById(R.id.imgEdit);
        holder.imgLike = convertView.findViewById(R.id.imgLike);

        final Congviec congviec = congviecArrayList.get(position);
        holder.txtTenCV.setText(congviec.getTenCV());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dialogSua(congviec.getId(), congviec.getTenCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.dialogXoa(congviec.getId(), congviec.getTenCV());
            }
        });

        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txtLike.getText().toString().trim().equals("Thích")){
                    holder.imgLike.setImageResource(R.drawable.ic_like_orange);
                    holder.txtLike.setText("Đã thích");
                    holder.txtLike.setTextColor(Color.parseColor("#fd6003"));
                }else{
                    holder.imgLike.setImageResource(R.drawable.ic_like);
                    holder.txtLike.setText("Thích");
                    holder.txtLike.setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });

        return convertView;
    }
}
