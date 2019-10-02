package com.example.dincerkizildere.notes;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MyUtil {
    public final static String PACKAGE_NAME = "tinyclipboardmanager";

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    public static String stringLengthCut(String string) {
        return stringLengthCut(string, 200);
    }

    public static String stringLengthCut(String string, int length) {
        string = string.trim();
        return  (string.length() > length) ?
                string.substring(0, length - 2).trim()+"…"
                : string.trim();
    }

    public static String getFormatDate(Context context, Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.date_format));
        return dateFormat.format(date);
    }

    public static String getFormatTime(Context context, Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.time_format));
        return dateFormat.format(date);
    }

    public static String getFormatTimeWithSecond(Context context, Date date) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(context.getString(R.string.time_format_with_second));
        return dateFormat.format(date);
    }

    public static class ResizeWidthAnimation extends Animation {
        private int mWidth;
        private int mStartWidth;
        private View mView;

        public ResizeWidthAnimation(View view, int width)
        {
            mView = view;
            mWidth = width;
            mStartWidth = view.getWidth();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            int newWidth = mStartWidth + (int) ((mWidth - mStartWidth) * interpolatedTime);

            mView.getLayoutParams().width = newWidth;
            mView.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight)
        {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds()
        {
            return true;
        }
    }

    public static void requestBackup(Context context) {
        Log.d(MyUtil.PACKAGE_NAME, "requestBackup");
        BackupManager backupManager = new BackupManager(context);
        backupManager.dataChanged();
    }

    public static SharedPreferences getLocalSharedPreferences(Context context) {
        return context.getSharedPreferences("LocalSharedPreference", Context.MODE_PRIVATE);
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAccessibilityEnabled(Context context, String id) {

        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = am
                .getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo service : runningServices) {
            if (service.getId().contains(id)) {
                return true;
            }
        }

        return false;
    }
    public static String generateHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            String base64 = Base64.encodeToString(byteData, Base64.NO_WRAP);
            return base64;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void vibrator(Context context) {
        //This need <uses-permission android:name="android.permission.VIBRATE" />
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(20);
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void openKeyboard(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 500);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


}
