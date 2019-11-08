package com.example.android.placeholder_inventory.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.placeholder_inventory.Models.Room;
import com.example.android.placeholder_inventory.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder> {
    private Context mContext;
    private DatabaseReference mRooms;
    private ChildEventListener mChildEventListener;
    private List<Room> mRoomList = new ArrayList<>();

    // Constructor - argument is an array of room names
    public RoomListAdapter(final Context context, DatabaseReference mData) {
        this.mRooms = mData;
        this.mContext = context;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Room room = dataSnapshot.getValue(Room.class);
                if (room == null){
                    Toast.makeText(mContext,
                            "Error: could not fetch room.",
                            Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(mContext, "The room is: " + room.name, Toast.LENGTH_SHORT).show();
                mRoomList.add(room);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Room room = dataSnapshot.getValue(Room.class);
                // To implement
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // To implement
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // To implement
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Failed to load rooms.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mRooms.addChildEventListener(childEventListener);

        mChildEventListener = childEventListener;
    }

    // ViewHolder that contains the recyclerviews given to it with the constructor
    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView roomNameTextView;
        public ImageView roomImageView;

        public RoomViewHolder(View itemView) {
            super(itemView);
                roomNameTextView = (TextView) itemView.findViewById(R.id.room_text);
                roomImageView = (ImageView) itemView.findViewById(R.id.room_image);
        }
    }

    // LayoutManager use. Creates instance of ViewHolder with recyclerViews in it
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new RoomViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false));
    }

    // LayoutManager use. Sets content to the recyclerViews in the ViewHolder
    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        // This function automatically loops through the positions
        Room room = mRoomList.get(position);
        holder.roomNameTextView.setText(room.name);
    }

    // LayoutManager use. Item count.
    @Override
    public int getItemCount() {
        return mRoomList.size();
    }


    public void clearListener(){
        if (mChildEventListener != null){
            mRooms.removeEventListener(mChildEventListener);
        }
    }



}
