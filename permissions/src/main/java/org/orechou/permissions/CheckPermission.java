package org.orechou.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {

    String DEFAULT_PERMISSION_DESC = "此功能需要开启权限才可使用~";
    String DEFAULT_SETTING_DESC = "此功能需要开启权限才可使用, 请去设置中开启";

    String[] permissions();

    String permissionDesc() default DEFAULT_PERMISSION_DESC;

    String settingDesc() default DEFAULT_SETTING_DESC;

//    String isBlock();

}
