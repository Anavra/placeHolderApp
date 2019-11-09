package com.example.android.placeholder_inventory.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.placeholder_inventory.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItemFragment extends Fragment {
    private OnFragmentInteractionListener callback;

    // Text field to add new item
    private EditText mNameField;
    private static final String REQUIRED = "Required";

    public AddItemFragment() {
        // Required empty public constructor
    }

    public void setOnFragmentInteractionListener(OnFragmentInteractionListener callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    public interface OnFragmentInteractionListener {
        void addNewRoom(String name);
        void launchShowListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View addItemView = inflater.inflate(R.layout.fragment_add_item, container,
                false);

        //Add new item field
        mNameField = addItemView.findViewById(R.id.name_field);

        // Buttons
        Button mAddNewButton = addItemView.findViewById(R.id.add_button);

        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final String name = mNameField.getText().toString();
                if (formIsValid(name)) {
                    callback.addNewRoom(name);
                    callback.launchShowListFragment();
                }
            }
        });

        return addItemView;
    }
    private boolean formIsValid(String name){
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(REQUIRED);
            return false;
        }
        else {
            return true;
        }
    }
}
