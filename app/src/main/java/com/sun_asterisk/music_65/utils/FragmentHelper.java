package com.sun_asterisk.music_65.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.sun_asterisk.music_65.R;

public class FragmentHelper {

    public static void replaceFragment(Fragment fragment, FragmentManager fragmentManager) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutContrainer, fragment);
        fragmentTransaction.commit();
    }
}
