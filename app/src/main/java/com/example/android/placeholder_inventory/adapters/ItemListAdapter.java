package com.example.android.placeholder_inventory.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.placeholder_inventory.R;
import com.example.android.placeholder_inventory.models.UserItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
    /**
     * This Adapter gets a databaseReference from FireBase and adapts it into
     * information that can be shown in the ShowListFragment (its view)
     */

    private final Context mContext;
    private final DatabaseReference mItems;
    private final ChildEventListener mChildEventListener;
    private final OnAdapterInteractionListener mClickListener;
    private final List<UserItem> mUserItemList = new ArrayList<>();
    private final String TAG = "ItemListAdapter";

    // Constructor - argument is db reference with rooms of the user
    public ItemListAdapter(final Context context, final DatabaseReference mData,
                           OnAdapterInteractionListener clickListener) {
        this.mItems = mData;
        this.mContext = context;
        this.mClickListener = clickListener;

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserItem userItem = dataSnapshot.getValue(UserItem.class);
                if (userItem == null) {
                    Log.e(TAG, "onChildAdded: no userItem.");
                }

                mUserItemList.add(userItem);
                notifyItemInserted(mUserItemList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserItem newUserItem = dataSnapshot.getValue(UserItem.class);
                String userKey = dataSnapshot.getKey();
                if (newUserItem == null) {
                    Log.e(TAG, "onChildChanged: no newUserItem.");
                }

                int index = mUserItemList.indexOf(userKey);
                if (index > -1) {
                    mUserItemList.set(index, newUserItem);
                    notifyItemChanged(index);
                } else {
                    Log.w(TAG, "onChildChanged: unknown_child:" + userKey);
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String userKey = dataSnapshot.getKey();

                int index = mUserItemList.indexOf(userKey);
                if (index > -1) {
                    mUserItemList.remove(index);
                    notifyItemRemoved(index);
                } else {
                    Log.w(TAG, "onChildRemoved: unknown_child:" + userKey);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // To implement
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(mContext, "Failed to load items.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mItems.addChildEventListener(childEventListener);

        mChildEventListener = childEventListener;
    }

    // LayoutManager use. Creates instance of ViewHolder with recyclerViews in it
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        view = inflater.inflate(R.layout.recyclerview_item, parent, false);

        return new ItemViewHolder(view);
    }

    // LayoutManager use. Sets content to the recyclerViews in the ViewHolder
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        // This function automatically loops through the positions
        UserItem userItem = mUserItemList.get(position);
        holder.itemNameTextView.setText(userItem.getName());
    }

    // LayoutManager use. Item count.
    @Override
    public int getItemCount() {
        return mUserItemList.size();
    }


    public void clearListener() {
        if (mChildEventListener != null) {
            mItems.removeEventListener(mChildEventListener);
        }
    }

    public interface OnAdapterInteractionListener {
        void onItemClick(String itemId);
    }

    // ViewHolder that contains the recyclerViews given to it with the constructor
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView itemNameTextView;
        final ImageView itemImageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.room_text);
            itemImageView = itemView.findViewById(R.id.room_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            UserItem userItem = mUserItemList.get(position);
            final String itemId = userItem.getItemId();
            mClickListener.onItemClick(itemId);
        }
    }


}
