package org.ussdplus;

import java.util.HashMap;

/**
 * @author Mário Júnior
 */
public class BaseUSSDSession extends HashMap<String,Object> implements USSDSession {

    private String currentWindow = null;

    public double getDouble(String key) {
        return 0;
    }

    public boolean is(String key) {
        return false;
    }

    public boolean getBoolean(String key) {
        return false;
    }

    public String getString(String key) {
        return null;
    }

    public int getInt(String key) {
        return 0;
    }

    public long getLong(String key) {
        return 0;
    }

    public <T> T get(String key, Class<T> type) {
        return null;
    }

    public void saveSession() {



    }

    public String getCurrentWindow() {

        return this.currentWindow;

    }

    public void setCurrentWindow(String windowName) {

        this.currentWindow = windowName;

    }
}
