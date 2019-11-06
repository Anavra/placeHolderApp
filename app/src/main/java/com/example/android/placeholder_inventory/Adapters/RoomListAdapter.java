package com.example.android.placeholder_inventory.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.placeholder_inventory.R;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    private String[] mRoomList;

    // Constructor - argument is an array of room names
    public RoomListAdapter(String[] mRoomList) {

        this.mRoomList = mRoomList;
    }

    // ViewHolder that contains the recyclerviews given to it with the constructor
    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomTextView;
        public ImageView roomImageView;

        public RoomViewHolder(View itemView) {
            super(itemView);
            if(itemView != null) {
                TextView roomTextView = (TextView) itemView.findViewById(R.id.room_text);
                this.roomTextView = roomTextView;
                ImageView roomImageView = (ImageView) itemView.findViewById(R.id.room_image);
                this.roomImageView = roomImageView;
            }
        }
    }

    // LayoutManager use. Creates instance of ViewHolder with recyclerViews in it
    @Override
    public RoomListAdapter.RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);
        return new RoomViewHolder(v);
    }

    // LayoutManager use. Sets content to the recyclerViews in the ViewHolder
    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        // This function automatically loops through the positions
        holder.roomTextView.setText(mRoomList[position]);
    }

    // LayoutManager use. Item count.
    @Override
    public int getItemCount() {
        return mRoomList.length;
    }

}
