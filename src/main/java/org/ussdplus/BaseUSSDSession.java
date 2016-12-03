package org.ussdplus;

import java.util.HashMap;

/**
 * @author Mário Júnior
 */
public abstract class BaseUSSDSession<Type extends Object> extends HashMap<String,Object> implements USSDSession<Type> {

    public double getDouble(String key) {

        try {

            if (this.containsKey(key))
                return Double.parseDouble(this.get(key).toString());

        }catch (Exception ex){/*TODO: Log*/}

        return 0.0;
    }

    public boolean is(String key) {

        try {

            if (this.containsKey(key))
                return Boolean.parseBoolean(this.get(key).toString());

        }catch (Exception ex){/*TODO: Log*/}

        return false;

    }

    public boolean getBoolean(String key) {

        return is(key);

    }

    public String getString(String key) {

        try {

            if (this.containsKey(key))
                return get(key).toString();

        }catch (Exception ex){/*TODO: Log*/}

        return null;

    }

    public int getInt(String key) {

        try {

            if (containsKey(key))
                return Integer.parseInt(get(key).toString());

        }catch (Exception ex){/*TODO: Log*/}

        return 0;
    }

    public long getLong(String key) {

        try {

            if (containsKey(key))
                return Long.parseLong(get(key).toString());

        }catch (Exception ex){/*TODO: Log*/}

        return 0L;

    }

    public <T> T get(String key, Class<T> type) {

        try {

            if (containsKey(key))
                return (T) get(key);


        }catch (Exception ex){/*TODO: Log*/}

        return null;

    }



}
