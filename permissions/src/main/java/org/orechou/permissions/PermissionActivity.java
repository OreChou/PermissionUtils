package org.orechou.permissions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class PermissionActivity extends Activity {

    private static final String PARAMS_PERMISSION_DESC = "permission_desc";
    private static final String PARAMS_SETTING_DESC = "setting_desc";
    private static final String PARAMS_PERMISSIONS = "permissions";

    public static OnPermissionCheckListener checkListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] permissions = getIntent().getStringArrayExtra(PARAMS_PERMISSIONS);
        PermissionUtils.checkPermission(this, permissions, checkListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.permissionResult(this, requestCode, permissions, grantResults, checkListener);
    }

    public static void checkPermission(Context context, @NonNull String[] permissions,
                                       String permissionDesc, String settingDesc,
                                       OnPermissionCheckListener listener) {
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PARAMS_PERMISSIONS, permissions);
        intent.putExtra(PARAMS_PERMISSION_DESC, permissionDesc);
        intent.putExtra(PARAMS_SETTING_DESC, settingDesc);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        checkListener = listener;
        context.startActivity(intent);
    }
}
