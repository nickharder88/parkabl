package com.nickharder88.parkabl.ui.main.property;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.model.Property;
import com.nickharder88.parkabl.ui.main.fragments.cards.CardAddressFragment;

public class PropertyFragment extends Fragment {

    private PropertyViewModel viewModel;

    public PropertyFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_property, container, false);

        final String propertyId = getArguments().getString("propertyId");
        if (propertyId == null) {
            throw new Error("Expected Property Id to be passed to Property Fragment");
        }

        // Add Child Fragments
        FragmentManager fm = getChildFragmentManager();
        Fragment scan = fm.findFragmentById(R.id.fragment_scan);
        CardAddressFragment cardAddress = (CardAddressFragment) fm.findFragmentById(R.id.fragment_card_address);

        if (scan == null) {
            fm.beginTransaction().add(R.id.fragment_scan, new ScanFragment()).commit();
        }

        if (cardAddress == null) {
            cardAddress = new CardAddressFragment();
            fm.beginTransaction().add(R.id.fragment_card_address, cardAddress).commit();
        }

        final CardAddressFragment finalCardAddress = cardAddress;
        viewModel = new PropertyViewModel(propertyId);
        viewModel.getProperty().observe(getViewLifecycleOwner(), new Observer<Property>() {
            @Override
            public void onChanged(Property property) {
                finalCardAddress.setAddress(property.data.address);
            }
        });

        return view;
    }
}
