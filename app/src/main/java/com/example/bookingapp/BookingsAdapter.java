package com.example.bookingapp;


import android.content.Context;
import android.content.DialogInterface;
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


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ImageViewHolder> {
    private Context mContext;
    private List<FetchData> mUploads;
    private OnItemClickListener mListener;

    public BookingsAdapter(Context mContext, ArrayList<FetchData> mUploads) {
        this.mContext = mContext;
        this.mUploads = mUploads;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_layout,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        FetchData uploadCurrent = mUploads.get(position);

        holder.mTvName.setText("Passenger : "+uploadCurrent.getPsName());
        holder.mTvPhone.setText("Phone : "+uploadCurrent.getPsPhone());
        holder.mTvTo.setText("To : "+uploadCurrent.getToFn());
        holder.mTvEmail.setText("Email : "+uploadCurrent.getPsEmail());
        holder.mTvTime.setText("Time : "+uploadCurrent.getTimeFn());
        holder.mTvFrom.setText("From : "+uploadCurrent.getFromFn());
        holder.mTvBus.setText("Bus Name : "+uploadCurrent.getNameFn());
        holder.mTvType.setText("Bus Type : "+uploadCurrent.getTypeFn());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView mTvName, mTvPhone,mTvTo,mTvEmail,mTvTime,mTvFrom,mTvBus,mTvType;

        public ImageViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.txt_fullName_bk_ly);
            mTvPhone = itemView.findViewById(R.id.txt_phone_bk_ly);
            mTvTo = itemView.findViewById(R.id.txt_to_bk_ly);
            mTvEmail = itemView.findViewById(R.id.txt_email_bk_ly);
            mTvTime = itemView.findViewById(R.id.txt_time_bk_ly);
            mTvFrom = itemView.findViewById(R.id.txt_from_bk_ly);
            mTvBus = itemView.findViewById(R.id.txt_bus_name_bk_ly);
            mTvType = itemView.findViewById(R.id.txt_bus_type_bk_ly);


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

    public void setSearchOperation(List<FetchData> newList){
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
