package com.munshig.shaw.munshig_business.AppUtilities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference extends Application
{
    static final String PREF_USER_NAME= "username";
    static final String PREF_TYPE = "User Type";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static void setUserType(Context context, Boolean type){
        if(type){
            SharedPreferences.Editor editor = getSharedPreferences(context).edit();
            editor.putBoolean(PREF_TYPE, type);
            editor.commit();
        }
    }

    public static Boolean getUserType(Context context){
        return getSharedPreferences(context).getBoolean(PREF_TYPE, false);
    }
}