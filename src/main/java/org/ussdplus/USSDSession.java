package org.ussdplus;

import java.util.Map;

/**
 * @author Mário Júnior
 */
public interface USSDSession extends Map<String,Object> {

    public double getDouble(String key);
    public boolean is(String key);
    public boolean getBoolean(String key);
    public String getString(String key);
    public int getInt(String key);
    public long getLong(String key);

    public <T> T get(String key, Class<T> type);

    public void saveSession();

    String getCurrentWindow();

    void setCurrentWindow(String windowName);

    void close();

}
