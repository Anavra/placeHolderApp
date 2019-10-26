package com.example.android.placeholder_inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    private String[] mRoomList;

    // Reference to the views for each data item
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

    // Create new views (invoked by layout manager)
    @Override
    public RoomListAdapter.RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.recyclerview_item, parent, false);

        return new RoomViewHolder(v);
    }

    // Replace the contents of a view (invoked by layout manager)
    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        holder.roomTextView.setText(mRoomList[position]);
        /*
        for (String room : mRoomList) {
            holder.textView.setText(room);
        }

         */
    }

    // Return size of the dataset (invoked by layout manager)
    @Override
    public int getItemCount() {
        return mRoomList.length;
    }


}
