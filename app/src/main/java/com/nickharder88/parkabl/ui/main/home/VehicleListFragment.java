package com.nickharder88.parkabl.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.nickharder88.parkabl.R;
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
        Query query = FirebaseFirestore.getInstance().collection("vehicles");

        FirestoreRecyclerOptions<Vehicle> response = new FirestoreRecyclerOptions.Builder<Vehicle>()
                .setQuery(query, Vehicle.class)
                .setLifecycleOwner(this)
                .build();

        return new FirestoreRecyclerAdapter<Vehicle, VehicleHolder>(response) {
            @NonNull
            @Override
            public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new VehicleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vehicle, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull VehicleHolder holder, int position, @NonNull Vehicle model) {
                holder.bind(model);
            }
        };

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_vehicle_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_vehicle:
                NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_createVehicleFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
