package com.bombimbom.sportviewer.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.bombimbom.sportviewer.R;

public class UIHelper {

    public static void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, (dialogInterface, i) -> {});
        builder.create().show();

    }

    public static boolean isTabletDevice(Context activityContext) {

        boolean device_large = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
        DisplayMetrics metrics = new DisplayMetrics();
        Activity activity = (Activity) activityContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (device_large) {
            //Tablet
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT){
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_TV){
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_HIGH){
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_280){
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_400) {
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_560) {
                return true;
            }else if(metrics.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
                return true;
            }
        }
        return false;
    }
}
