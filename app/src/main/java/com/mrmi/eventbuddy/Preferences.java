package com.mrmi.eventbuddy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class Preferences {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor sharedPreferencesEditor;

    /* A note about using string sets in shared preferences found after hours of searching:
       Note that you must not modify the set instance returned by this call.
       The consistency of the stored data is not guaranteed if you do, nor is your ability to modify the instance at all.
       => new HashSet<>(stringSet) when editing or saving
     */
    public Preferences (Context context) {
        sharedPreferences = context.getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
    }

    public void saveStringSet(String key, Set<String> stringSet) {
        sharedPreferencesEditor.putStringSet(key, new HashSet<>(stringSet)).apply();
        System.out.println("[MRMI]: PREFERENCES: SAVED STRING SET WITH KEY " + key + " SET " + stringSet);
    }

    public Set<String> getStringSet(String key) {
        System.out.println("[MRMI]: PREFERENCES: GETTING STRING SET WITH KEY " + key);
        return new HashSet<>(sharedPreferences.getStringSet(key, new HashSet<>()));
    }

    public void addStringToSet(String key, String string) {
        System.out.println("[MRMI]: PREFERENCES: ADDING STRING " + string + " TO SET " + key);
        Set<String> stringSet = getStringSet(key);
        stringSet.add(string);
        saveStringSet(key, stringSet);
    }

    public void removeStringFromSet(String key, String string) {
        System.out.println("[MRMI]: PREFERENCES: REMOVING STRING " + string + " FROM SET " + key);

        Set<String> stringSet = getStringSet(key);
        stringSet.remove(string);
        saveStringSet(key, stringSet);
    }
}
