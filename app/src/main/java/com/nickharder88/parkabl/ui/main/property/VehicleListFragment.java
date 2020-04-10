package com.nickharder88.parkabl.ui.main.property;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.VehicleDTO;
import com.nickharder88.parkabl.data.model.Vehicle;

public class VehicleListFragment extends Fragment {

    private RecyclerView mVehicleRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        mVehicleRecyclerView = view.findViewById(R.id.vehicle_recycler_view);
        mVehicleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mVehicleRecyclerView.setAdapter(updateUI());
        return view;
    }

    private RecyclerView.Adapter updateUI() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // TODO PICK UP HERE
        Query query = db.collection(Vehicle.collection);

        // Get Property
        // Get Tenants At Property
        // Get Vehicles for each Tenant

        FirestoreRecyclerOptions<VehicleDTO> response = new FirestoreRecyclerOptions.Builder<VehicleDTO>()
                .setQuery(query, VehicleDTO.class)
                .setLifecycleOwner(this)
                .build();

        return new FirestoreRecyclerAdapter<VehicleDTO, VehicleHolder>(response) {
            @NonNull
            @Override
            public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new VehicleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vehicle, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull VehicleHolder holder, int position, @NonNull VehicleDTO model) {
                holder.bind(model);
            }
        };

    }
}
