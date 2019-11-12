package com.example.android.placeholder_inventory.Fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.placeholder_inventory.R;

public class DetailsFragment extends Fragment {
    //private DetailsViewModel mViewModel;
    private TextView mItemName;

    /**public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }
     */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View detailsView = inflater.inflate(R.layout.details_fragment, container, false);
        mItemName = detailsView.findViewById(R.id.details_item_name);
        Bundle args = getArguments();
        mItemName.setText(args.getString("itemId"));
        if (mItemName == null) {
            throw new IllegalArgumentException("Must pass itemId");
        }

        return detailsView;
    }

    /**
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        // TODO: Use the ViewModel
    }
    **/
}
