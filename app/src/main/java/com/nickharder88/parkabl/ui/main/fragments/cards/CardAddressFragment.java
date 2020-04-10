package com.nickharder88.parkabl.ui.main.fragments.cards;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.AddressDTO;
import com.nickharder88.parkabl.data.model.Address;

public class CardAddressFragment extends Fragment {

    private String TAG = "CardAddressFragment";


    TextView textAddressApartmentNum;
    TextView textAddressHouseNum;
    TextView textAddressStreet;
    TextView textAddressCity;
    TextView textAddressState;
    TextView textAddressCountry;
    TextView textAddressPostal;

    public CardAddressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_address, container, false);

        textAddressApartmentNum = view.findViewById(R.id.text_address_apartmentNum);
        textAddressHouseNum = view.findViewById(R.id.text_address_houseNum);
        textAddressStreet = view.findViewById(R.id.text_address_street);
        textAddressCity = view.findViewById(R.id.text_address_city);
        textAddressState = view.findViewById(R.id.text_address_state);
        textAddressCountry = view.findViewById(R.id.text_address_country);
        textAddressPostal = view.findViewById(R.id.text_address_postal);

        return view;
    }

    public void setAddress(String addressId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Address.collection).document(addressId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Could not load Address");
                    return;
                }

                DocumentSnapshot snap = task.getResult();
                AddressDTO addressDTO = snap.toObject(AddressDTO.class);
                textAddressApartmentNum.setText(addressDTO.apartmentNum);
                textAddressHouseNum.setText(addressDTO.houseNum);
                textAddressStreet.setText(addressDTO.street);
                textAddressCity.setText(addressDTO.city);
                textAddressState.setText(addressDTO.state);
                textAddressCountry.setText(addressDTO.country);
                textAddressPostal.setText(addressDTO.postal);
            }
        });
    }
}
