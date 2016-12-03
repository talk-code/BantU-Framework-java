package org.ussdplus;

import java.util.Date;

/**
 * @author Mário Júnior
 */
public abstract class BasicSession extends BaseUSSDSession<Long> {

    private long id;
    private String currentWindow;
    private String previousWindow;

    public BasicSession(){

        this.id = new Date().getTime();

    }

    public Long getId() {

        return id;
    }

    public String getCurrentWindow() {
        return this.currentWindow;
    }

    public void setCurrentWindow(String windowName) {

        this.currentWindow = windowName;

    }

    public void setPreviousWindow(String windowName) {

        this.previousWindow = windowName;

    }

    public String getPreviousWindow(String windowName) {

        return this.previousWindow;

    }

    public void close() {

        this.clear();
        this.currentWindow = null;
        this.previousWindow = null;

    }
}
