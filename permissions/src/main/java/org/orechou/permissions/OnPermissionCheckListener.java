package org.orechou.permissions;

import android.app.Activity;

public interface OnPermissionCheckListener {

    void onGranted(Activity context);

    void onReason(Activity context);

    void onGuide(Activity context);

}
