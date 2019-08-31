package org.orechou.permissions;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class PermissionAspect {

    private static final String TAG = PermissionAspect.class.getName();

    @Pointcut("execution(@org.orechou.permissions.CheckPermission * *(..))")
    public void executionCheckPermission() {

    }

    @Around("executionCheckPermission()")
    public void checkPermission(final ProceedingJoinPoint joinPoint) {
        Log.d(TAG, "executionCheckPermission before");
        Object caller = joinPoint.getThis();
        Context context = getContext(caller);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature != null ? signature.getMethod() : null;
        if (method == null) {
            Log.d(TAG, "Declare CheckPermission annotation error.");
            return;
        }
        final CheckPermission checkPermission = method.getAnnotation(CheckPermission.class);
        if (checkPermission == null) {
            return;
        }
        PermissionActivity.checkPermission(context, checkPermission.permissions(),
                checkPermission.permissionDesc(), checkPermission.settingDesc(),
                new OnPermissionCheckListener() {
                    @Override
                    public void onGranted(Activity context) {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        context.finish();
                    }
                    @Override
                    public void onReason(Activity context) {
                        AlertStyle alertStyle = new DefaultAlertStyle();
                        alertStyle.onExplain(context, checkPermission.permissionDesc());
                        context.finish();
                    }

                    @Override
                    public void onGuide(Activity context) {
                        AlertStyle alertStyle = new DefaultAlertStyle();
                        alertStyle.onSettingGuide(context, checkPermission.permissionDesc());
                    }
                });
    }

    private Context getContext(Object object) {
        Context context = null;
        if (object instanceof Activity || object instanceof Application || object instanceof Service) {
            context = (Context) object;
        } else if (object instanceof ContentProvider) {
            context = ((ContentProvider) object).getContext();
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof Dialog) {
            context = ((Dialog) object).getContext();
        } else if (object instanceof View) {
            context = ((View) object).getContext();
        }
        return context;
    }

}
