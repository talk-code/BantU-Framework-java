package org.ussdplus;

import java.util.regex.Matcher;

/**
 * @author Mário Júnior
 */
public interface USSDRequest {

    String getMSISDN();

    void setMSISDN(String MSISDN);

    String getMCC();
    void setMCC(String value);

    String getCID();
    void setCID(String value);

    String getLAC();
    void setLAC(String value);

    void setAttachment(Object attachment);
    <T> T getAttachment(Class<T> tClass);
    Object getAttachment();

    /**
     * Request input values is set to null
     * @param windowName
     * @param session
     * @param response
     */
    void redirectTo(String windowName,USSDSession session, USSDResponse response);

    void setApplication(USSDApplication application);

    USSDApplication getApplication();


}
