package org.ussdplus;

import java.util.regex.Matcher;

/**
 * @author Mário Júnior
 */
public interface USSDRequest {

    String getInputValue();

    void setInputValue(String inputValue);

    String getUSSDIN();

    void setUSSDIN(String USSDIN);

    String getUssdCode();

    void setUssdCode(String ussdCode);

    /**
     * Request input values is set to null
     * @param windowName
     * @param session
     * @param response
     */
    void redirectTo(String windowName,USSDSession session, USSDResponse response);

    void setApplication(USSDApplication application);

    USSDApplication getApplication();

    Matcher getInputRegexpMatcher();

    void setInputRegexpMatcher(Matcher matcher);


}
