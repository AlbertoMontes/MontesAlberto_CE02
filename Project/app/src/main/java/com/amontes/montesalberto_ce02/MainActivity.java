// Alberto Montes
// MDF3 1604
// CE02

package com.amontes.montesalberto_ce02;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GridViewFrag.ClickInterface {

    //private static final String ACTION_VIEW = ;
    CustomGridAdapter mCustomGridAdapter;
    private String TAG = MainActivity.class.getSimpleName();
    // Permission request code lower 8 bits.
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get user's permission on runtime.
        checkPerm();

        if (savedInstanceState == null) {

            FragmentManager mgr = getFragmentManager();
            FragmentTransaction trans = mgr.beginTransaction();
            GridViewFrag frag = GridViewFrag.newInstance();
            trans.replace(R.id.frag_container, frag, GridViewFrag.TAG).commit();

        }

    }

    // Implemented methods.
    @Override
    public void onRefresh() {

        Log.d(TAG, "INTERFACE OPERATING");
        // Explicitly start Service with no extras.
        Intent intent = new Intent(this, ImageService.class);
        startService(intent);

    }

    @Override
    public void imageClick(File file) {

        // Show full image in gallery.
        Intent intent = new Intent();
        intent.setAction(ACTION_VIEW);
        Uri uri = Uri.parse("file://" + "" + String.valueOf(file));
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);

    }

    // Permissions method.
    private void checkPerm() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Explain rationale behind permission here.
                Toast.makeText(getApplicationContext(), "Permission needed to save photos", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            }

        }

    }

    // Broadcast Receiver to update GridView.
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Update GridView with saved images.
            File imgFile = new File(getExternalFilesDir("com.montesalberto_ce02") + "/Lab2Data");
            ArrayList<File> imageFiles = new ArrayList<>(Arrays.asList(imgFile.listFiles()));
            mCustomGridAdapter = new CustomGridAdapter(context, R.layout.grid_image_view, imageFiles);
            GridViewFrag.imageGrid.setAdapter(mCustomGridAdapter);

        }

    };

    // Dynamic BroadcastReceiver registered/unregistered.
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.amontes.montesalberto_ce02.UPDATE_UI");
        registerReceiver(myReceiver, filter);

    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(myReceiver);

    }

}