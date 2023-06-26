package com.example.renthouse.SharedPreferences;

import static com.gun0912.tedpermission.provider.TedPermissionProvider.context;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class DataLocalManager {
    private static final String PREF_NAME = "SearchHistory";
    private static final String KEY_HISTORY = "history";
    private static DataLocalManager instance;
    private static SharedPreferences sharedPreferences;

    private DataLocalManager(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized DataLocalManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataLocalManager(context);
        }
        return instance;
    }

    public static Set<String> getSearchHistory() {
        return sharedPreferences.getStringSet(KEY_HISTORY, new HashSet<>());
    }

    public static void addSearchHistory(String keyword) {
        Set<String> searchHistorySet = getSearchHistory();
        searchHistorySet.add(keyword);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_HISTORY, searchHistorySet);
        editor.commit();
    }
}
