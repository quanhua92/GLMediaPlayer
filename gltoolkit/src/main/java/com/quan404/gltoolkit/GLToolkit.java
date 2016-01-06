package com.quan404.gltoolkit;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Resources;
import android.os.Build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by quanhua on 04/01/2016.
 */
public class GLToolkit {

    public static boolean checkSupportGLES2(Context context){
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
//        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000; // works on real devices

        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000 // modify to work on emulator
                                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                                    && (Build.FINGERPRINT.startsWith("generic")
                                    || Build.FINGERPRINT.startsWith("unknown")
                                    || Build.MODEL.contains("google_sdk")
                                    || Build.MODEL.contains("Emulator")
                                    || Build.MODEL.contains("Android SDK built for x86")));

        return supportsEs2;
    }
}
