package com.nickharder88.parkabl.ui.welcome;

import androidx.fragment.app.Fragment;

import com.nickharder88.parkabl.SingleFragmentActivity;

public class WelcomeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new WelcomeFragment();
    }
}
