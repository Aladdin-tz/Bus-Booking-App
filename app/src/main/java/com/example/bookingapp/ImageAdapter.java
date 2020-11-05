package com.example.bookingapp;


import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context mContext, List<Upload> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.bus_name_ad.setText("Bus Name : "+ uploadCurrent.getBusName());
        holder.bus_type_ad.setText("Bus Type : "+ uploadCurrent.getBusType());
        holder.bus_from_ad.setText("From : "+ uploadCurrent.getBusFrom());
        holder.bus_to_ad.setText("To : "+ uploadCurrent.getBusTo());
        holder.bus_time_ad.setText("Time : "+ uploadCurrent.getBusTime());
        holder.bus_delete_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("DELETING BUS");
                builder.setMessage("Are you sure you want to delete?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("uploads/"+uploadCurrent.getKey());
                        ref.removeValue();
                        message("SUCCESS!!","Bus Deleted Successfully");
                    }
                });
                builder.create().show();

            }
        });
        Glide.with(mContext).load(uploadCurrent.getImageUrl()).placeholder(R.mipmap.bus_land_page)
                .centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView bus_name_ad;
        public TextView bus_type_ad;
        public TextView bus_from_ad;
        public TextView bus_to_ad;
        public TextView bus_time_ad;
        public TextView bus_delete_ad;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            bus_name_ad = itemView.findViewById(R.id.bus_name_ly);
            bus_type_ad = itemView.findViewById(R.id.bus_type_ly);
            bus_from_ad = itemView.findViewById(R.id.bus_from_ly);
            bus_to_ad = itemView.findViewById(R.id.bus_to_ly);
            bus_time_ad = itemView.findViewById(R.id.bus_time_ly);
            bus_delete_ad = itemView.findViewById(R.id.delete_bus);
            imageView = itemView.findViewById(R.id.img_ly);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }
        // Handle Menu Items
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1,"Do Whatever");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener!=null){
                //Get the position of the clicked item
                int position = getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhatEverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhatEverClick(int position);

        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setSearchOperation(List<Upload> newList){
        mUploads = new ArrayList<>();
        mUploads.addAll(newList);
        notifyDataSetChanged();
    }
    public  void message(String tittle,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
