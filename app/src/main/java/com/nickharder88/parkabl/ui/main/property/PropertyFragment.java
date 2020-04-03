package com.nickharder88.parkabl.ui.main.property;

import android.os.Bundle;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import com.nickharder88.parkabl.R;

public class PropertyFragment extends Fragment {

    public PropertyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property, container, false);

        TextView property = view.findViewById(R.id.text_view_property);
        property.setText(getArguments().getString("propertyId"));

        // Add Child Fragments
        FragmentManager fm = getChildFragmentManager();
        Fragment scan = fm.findFragmentById(R.id.fragment_scan);

        if (scan == null) {
            fm.beginTransaction().add(R.id.fragment_scan, new ScanFragment()).commit();
        }

        return view;
    }
}
