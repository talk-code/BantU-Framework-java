package org.bantu;

/**
 * @author Mário Júnior
 */
public interface USSDResponse  {

    String OK_STATUS="OK";
    String ERROR_STATUS="ERROR";

    Window getWindow();

    void setWindow(Window window);

    ResponseType getResponseType();

    void setResponseType(ResponseType responseType);

    void setResponseBody(String body);

    String getResponseBody();

    USSDSession getSession();

    void setSession(USSDSession session);

    String getStatus();
    void setStatus(String value);

}
