package com.nikitagordia.aplay.Abstract;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 20.12.17.
 */

public class FragmentContainerActivity extends AppCompatActivity {

    protected void putFragment(int container, Fragment newFragment) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(container);

        if (fragment == null)
            fm.beginTransaction()
                    .add(container, newFragment)
                    .commit();
    }

     protected void setUpPermissions(String[] perm) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < perm.length; i++)
                if (checkSelfPermission(perm[i]) != PackageManager.PERMISSION_GRANTED)
                    requestPermissions(new String[]{ perm[i] }, 0);
        }
     }
}
