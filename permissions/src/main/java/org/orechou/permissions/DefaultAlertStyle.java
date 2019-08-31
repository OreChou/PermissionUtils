package org.orechou.permissions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DefaultAlertStyle implements AlertStyle {

    @Override
    public void onExplain(Activity context, String reason) {
        Toast.makeText(context, reason, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSettingGuide(@NonNull final Activity context, String settingDesc) {
        if (TextUtils.isEmpty(settingDesc)) {
            context.finish();
            return;
        }

        new AlertDialog.Builder(context)
                .setMessage(settingDesc)
                .setNegativeButton("暂不", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("去开启", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "无法打开应用详情，请手动开启权限~", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                })
                .setOnDismissListener((dialog) -> context.finish())
                .show();
    }

}
