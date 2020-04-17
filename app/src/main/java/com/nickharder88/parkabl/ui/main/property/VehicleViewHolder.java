package com.nickharder88.parkabl.ui.main.property;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.TenantDTO;
import com.nickharder88.parkabl.data.model.Tenant;
import com.nickharder88.parkabl.data.model.Vehicle;

public class VehicleViewHolder extends RecyclerView.ViewHolder {

    private ImageView mImage;
    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;
    private TextView mState;
    private TextView mTenantPhone;

    public VehicleViewHolder(View itemView) {
        super(itemView);
        mImage = itemView.findViewById(R.id.vehicle_image);
        mLicense = itemView.findViewById(R.id.vehicle_license);
        mState = itemView.findViewById(R.id.vehicle_state);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mTenantPhone = itemView.findViewById(R.id.tenant_phone);
    }

    public void bind(Vehicle vehicle) {
        mLicense.setText(vehicle.data.licensePlateNum);
        mState.setText(vehicle.data.state);
        mMake.setText(vehicle.data.make);
        mModel.setText(vehicle.data.model);

        final StorageReference reference = FirebaseStorage.getInstance().getReference("vehicles/images/thumb_" + vehicle.id + ":image");
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mImage.getContext()).load(reference).into(mImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("STORAGE", "Object does not exist");
            }
        });

        if (vehicle.data.tenant != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Tenant.collection).document(vehicle.data.tenant).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
}
