package com.nickharder88.parkabl.ui.main.property;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.TenantDTO;
import com.nickharder88.parkabl.data.dto.VehicleDTO;
import com.nickharder88.parkabl.data.model.Tenant;

public class VehicleViewHolder extends RecyclerView.ViewHolder {

    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;
    private TextView mState;
    private TextView mTenantPhone;

    public VehicleViewHolder(View itemView) {
        super(itemView);
        mLicense = itemView.findViewById(R.id.vehicle_license);
        mState = itemView.findViewById(R.id.vehicle_state);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mTenantPhone = itemView.findViewById(R.id.tenant_phone);
    }

    public void bind(VehicleDTO vehicle) {
        mLicense.setText(vehicle.licensePlateNum);
        mState.setText(vehicle.state);
        mMake.setText(vehicle.make);
        mModel.setText(vehicle.model);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Tenant.collection).document(vehicle.tenant).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snap = task.getResult();
                    if (snap != null) {
                        TenantDTO tenant = snap.toObject(TenantDTO.class);
                        if (tenant != null) {
                            mTenantPhone.setText(tenant.phone);
                        }
                    }
                }
            }
        });
    }
}
