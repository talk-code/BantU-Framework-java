package org.ussdplus;

import org.emerjoin.orbitussd.*;

/**
 * @author Mário Júnior
 */
public class BaseUSSDResponse implements USSDResponse {

    private Window window;
    private ResponseType responseType;
    private USSDSession session;

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

    public String toString(){

        return USSDPlus.getWindowRenderer().render(getWindow(),this);

    }

}
