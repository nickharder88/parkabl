package com.nickharder88.parkabl.ui.main.fragments.cards;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.LandlordDTO;
import com.nickharder88.parkabl.data.model.Landlord;

public class CardLandlordFragment extends Fragment {

    private String TAG = "CardLandlordFragment";

    TextView textLandlordName;

    public CardLandlordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_landlord, container, false);
        textLandlordName = view.findViewById(R.id.text_landlord_name);
        return view;
    }

    public void setLandlord(String landlordId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Landlord.collection).document(landlordId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e(TAG, "Could not load Landlord");
                    return;
                }

                DocumentSnapshot snap = task.getResult();
                LandlordDTO landlordDTO = snap.toObject(LandlordDTO.class);
                textLandlordName.setText(landlordDTO.name);
            }
        });
    }
}
