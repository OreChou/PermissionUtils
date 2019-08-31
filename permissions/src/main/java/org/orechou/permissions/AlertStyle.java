package org.orechou.permissions;

import android.app.Activity;

public interface AlertStyle {

    void onExplain(Activity context, String reason);

    void onSettingGuide(Activity context, String settingDesc);

}
