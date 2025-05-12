package com.example.todo_app_sp;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Date;

/**
 * Session manager to handle user session with SharedPreferences
 * Session expires after 2 minutes (120000 milliseconds)
 */
public class SessionManager {
    private static final String PREF_NAME = "TodoAppSession";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EXPIRES = "expires";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    // Session timeout in milliseconds (2 minutes)
    private static final long SESSION_TIMEOUT = 120000;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(int userId, String username) {
        // Set login status to true
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);

        // Set session expiration time (current time + 2 minutes)
        long expires = new Date().getTime() + SESSION_TIMEOUT;
        editor.putLong(KEY_EXPIRES, expires);

        editor.commit();
    }

    /**
     * Check if user is logged in and session is not expired
     */
    public boolean isLoggedIn() {
        if (!pref.getBoolean(KEY_IS_LOGGED_IN, false)) {
            return false;
        }

        // Check if session is expired
        long currentTime = new Date().getTime();
        long expires = pref.getLong(KEY_EXPIRES, 0);

        if (currentTime > expires) {
            // Session expired
            logoutUser();
            return false;
        }

        return true;
    }

    /**
     * Get stored session data (user ID)
     */
    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1);
    }

    /**
     * Get stored session data (username)
     */
    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        editor.clear();
        editor.commit();
    }

    /**
     * Check if session is about to expire (within 30 seconds)
     */
    public boolean isSessionAboutToExpire() {
        if (!isLoggedIn()) {
            return false;
        }

        long currentTime = new Date().getTime();
        long expires = pref.getLong(KEY_EXPIRES, 0);
        long timeLeft = expires - currentTime;

        return timeLeft <= 30000; // 30 seconds left
    }
}