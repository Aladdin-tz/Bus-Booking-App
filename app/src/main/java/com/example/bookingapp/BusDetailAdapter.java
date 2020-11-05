package com.example.bookingapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class BusDetailAdapter extends RecyclerView.Adapter<BusDetailAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Detail> mUploads;
    private OnItemClickListener mListener;

    public BusDetailAdapter(Context mContext, List<Detail> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.bus_details_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Detail uploadCurrent = mUploads.get(position);

        holder.txtTypeDetail .setText(uploadCurrent.getBusTypeDetail());
        holder.txtAcDetail.setText(uploadCurrent.getAcDetail());
        holder.txtChargeDetail.setText(uploadCurrent.getChargeDetail());
        holder.txtBiteDetail.setText(uploadCurrent.getBiteDetail());
        holder.txtAmountDetail.setText(uploadCurrent.getAmountDetail());

        Glide.with(mContext).load(uploadCurrent.getBusImg()).into(holder.imageDetail);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView txtTypeDetail,txtAcDetail,txtChargeDetail,txtBiteDetail,txtAmountDetail;
        ImageView imageDetail;
        public ImageViewHolder(View itemView) {
            super(itemView);
            txtTypeDetail = itemView.findViewById(R.id.bus_type_detail_ly);
            txtAcDetail = itemView.findViewById(R.id.bus_ac_detail_ly);
            txtChargeDetail = itemView.findViewById(R.id.bus_charge_detail_ly);
            txtBiteDetail = itemView.findViewById(R.id.bus_bites_detail_ly);
            txtAmountDetail = itemView.findViewById(R.id.bus_amount_detail_ly);
            imageDetail = itemView.findViewById(R.id.img_detail_ly);

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
            MenuItem doWhatever = menu.add(Menu.NONE, 1, 1,"Verify code");
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
                            mListener.onVerifyClick(position);
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

        void onVerifyClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public void setSearchOperation(List<Detail> newList){
        mUploads = new ArrayList<>();
        mUploads.addAll(newList);
        notifyDataSetChanged();
    }

}
