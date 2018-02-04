package com.nikitagordia.aplay;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Managers.DBManager;
import com.nikitagordia.aplay.Managers.FilesManager;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.List;

/**
 * Created by root on 1/22/18.
 */

public class SplashActivity extends FragmentContainerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareFileLoading();
    }

    private void prepareFileLoading() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int perm = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if (perm != PackageManager.PERMISSION_GRANTED) {
                setUpPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
            } else {
                if (perm == PackageManager.PERMISSION_GRANTED) {
                    loading();
                } else finish();
            }
        } else {
            loading();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) finish();
        loading();
    }

    private void loading() {
        List<AudioTrack> list;
        try {
            list = FilesManager.getAudioFiles(this);
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.perm_denied), Toast.LENGTH_SHORT);
            return;
        }
        MusicManager.get().setList(list);
        DBManager.get(this).updateAudioTracks(list);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
