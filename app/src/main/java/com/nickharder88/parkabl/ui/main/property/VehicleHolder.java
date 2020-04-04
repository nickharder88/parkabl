package com.nickharder88.parkabl.ui.main.property;

import android.view.View;
import android.widget.TextView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.nickharder88.parkabl.R;
import com.nickharder88.parkabl.data.dto.VehicleDTO;

public class VehicleHolder extends RecyclerView.ViewHolder {

    private TextView mMake;
    private TextView mModel;
    private TextView mLicense;

    private VehicleDTO mDto;

    public VehicleHolder(View itemView) {
        super(itemView);
        mMake = itemView.findViewById(R.id.vehicle_make);
        mModel = itemView.findViewById(R.id.vehicle_model);
        mLicense = itemView.findViewById(R.id.vehicle_license);

    }

    public void bind(VehicleDTO vehicle) {
        mDto = vehicle;
        mMake.setText(mDto.make);
        mModel.setText(mDto.model);
        mLicense.setText(mDto.license);
    }
}
