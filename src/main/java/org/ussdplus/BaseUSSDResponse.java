package org.ussdplus;

import org.ussdplus.*;

/**
 * @author Mário Júnior
 */
public class BaseUSSDResponse implements USSDResponse {

    private Window window;
    private ResponseType responseType;
    private USSDSession session;
    private String status = USSDResponse.OK_STATUS;

    protected BaseUSSDResponse(){

        super();

    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public USSDSession getSession() {
        return session;
    }

    public void setSession(USSDSession session) {
        this.session = session;
    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String value) {

        if(value==null)
            throw new IllegalArgumentException("Status should never be null");
        this.status = value;

    }

    public String toString(){

        return USSDPlus.getWindowRenderer().render(getWindow(),this);

    }



    public boolean requestFailed(){

        return this.status.equals(USSDResponse.ERROR_STATUS);

    }

}
