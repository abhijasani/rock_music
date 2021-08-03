package com.hvc.rockmusic.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class PermissionHelper {
    private static final int REQUEST_CODE = 1 << 2;

    public static void requestPermission(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                DialogHelper.showRationalePermissionDialog(activity, new DialogHelper.OnPositiveButtonListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                REQUEST_CODE);
                    }
                });
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE);
            }
        } else {
            //Call whatever you want
        }
    }

    public static void onRequestPermissionsResult(final Activity activity, int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity.recreate();
                } else {
                    activity.finish();
                }
                return;
            }
        }
    }

}
