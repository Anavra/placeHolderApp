package com.example.android.placeholder_inventory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    private String[] mRoomList;

    // ViewHolder that contains the recyclerviews given to it with the constructor
    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomTextView;
        public RoomViewHolder(View v) {
            super(v);
            TextView roomTextView = (TextView) v.findViewById(R.id.room_text);
            this.roomTextView = roomTextView;
        }
    }

    // Constructor - argument is an array of room names
    public RoomListAdapter(String[] myRoomList) {
        mRoomList = myRoomList;
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
