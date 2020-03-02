package com.nickharder88.parkabl.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import com.nickharder88.parkabl.data.model.Vehicle;
import com.nickharder88.parkabl.ui.new_vehicle.CreateVehicleActivity;

public class VehicleListFragment extends Fragment {

    private final String TAG = "VehicleListFragment";

    private RecyclerView mVehicleRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_list, container, false);

        mVehicleRecyclerView = view.findViewById(R.id.vehicle_recycler_view);
        mVehicleRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mVehicleRecyclerView.setAdapter(updateUI());

        Log.i(TAG, "Fragment in onCreateView");
        Log.i(TAG, String.valueOf(mVehicleRecyclerView.getAdapter().getItemCount()) + " items in adapter");

        return view;
    }

    private RecyclerView.Adapter updateUI() {
//        VehicleRepository repo = new VehicleRepository(getContext());
//
//        List<Vehicle> vehicles = new ArrayList<>();
//        vehicles.add(new Vehicle("Kia", "Forte", "1234567"));
//        vehicles.add(new Vehicle("Kia", "Sorento", "123458"));

        Query query = FirebaseFirestore.getInstance().collection("vehicles");

        FirestoreRecyclerOptions<Vehicle> response = new FirestoreRecyclerOptions.Builder<Vehicle>()
                .setQuery(query, Vehicle.class)
                .setLifecycleOwner(this)
                .build();

        return new FirestoreRecyclerAdapter<Vehicle, VehicleHolder>(response) {
            @NonNull
            @Override
            public VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                Log.d(TAG, "in onCreateViewHolder");
                return new VehicleHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_vehicle, parent, false), getActivity());
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
                final Intent intent = new Intent(getActivity(), CreateVehicleActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
