package com.example.renthouse.utilities;

import android.content.Context;
import android.content.pm.PackageManager;

public class CacheUtils {
    public static void clearCache(Context context) {
        try {
            context.getApplicationContext().getCacheDir().delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
