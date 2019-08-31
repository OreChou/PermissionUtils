package org.orechou.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    private static final String TAG = PermissionUtils.class.getName();

    private final static int PERMISSION_REQUEST_CODE = 1;

    public static boolean hasPermission(@NonNull Context context, String... permissions) {
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED
            || PermissionChecker.checkSelfPermission(context, permissions[i]) != PermissionChecker.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void checkPermission(@NonNull Activity context, String[] permissions, OnPermissionCheckListener listener) {
        if (permissions == null ||  permissions.length == 0) {
            if (listener != null) {
                listener.onGranted(context);
            }
            return;
        }

        List<String> refusedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (!hasPermission(context, permissions[i])) {
                refusedPermissions.add(permissions[i]);
            }
        }

        // 全部都有权限
        if (refusedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(context, refusedPermissions.toArray(new String[refusedPermissions.size()]),
                    PERMISSION_REQUEST_CODE);
        }
        // 全部都有权限
        else {
            listener.onGranted(context);
        }
    }

    public static void permissionResult(final Activity context, int requestCode, String[] permissions,
                                        int[] grantResults, OnPermissionCheckListener listener) {
        if (requestCode != PERMISSION_REQUEST_CODE) {
            return;
        }
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context, permissions[i])) {
                    listener.onReason(context);
                } else {
                    listener.onGuide(context);
                }
                return;
            }
        }
        listener.onGranted(context);
    }

}
