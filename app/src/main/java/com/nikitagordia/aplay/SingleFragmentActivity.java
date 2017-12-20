package com.nikitagordia.aplay;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by root on 20.12.17.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        setUpPermissions(new String[]{});
    }

     private void setUpPermissions(String[] perm) {
        if (perm.length == 0) return;
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             ArrayList<String> needPerm = new ArrayList<>();
             for (int i = 0; i < perm.length; i++)
                 if (checkSelfPermission(perm[i]) != PackageManager.PERMISSION_GRANTED)
                     needPerm.add(perm[i]);
             String[] req = new String[needPerm.size()];
             int pos = 0;
             for (String str : needPerm)
                 req[pos++] = str;
             requestPermissions(req, 0);
         }
     }
}
