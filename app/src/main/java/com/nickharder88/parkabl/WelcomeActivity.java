package com.nickharder88.parkabl;

import androidx.fragment.app.Fragment;

public class WelcomeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new WelcomeFragment();
    }
}
