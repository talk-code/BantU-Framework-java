package org.ussdplus;

/**
 * @author Mário Júnior
 */
public interface USSDResponse  {

    Window getWindow();

    void setWindow(Window window);

    ResponseType getResponseType();

    void setResponseType(ResponseType responseType);

    USSDSession getSession();

    void setSession(USSDSession session);

}
