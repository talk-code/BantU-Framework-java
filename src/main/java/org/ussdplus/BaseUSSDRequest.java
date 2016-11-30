package org.ussdplus;

import org.ussdplus.*;

import java.util.regex.Matcher;

/**
 * @author Mário Júnior
 */
public class BaseUSSDRequest implements USSDRequest {

    private String inputValue;
    private String USSDIN;
    private String ussdCode;
    private USSDApplication application;
    private Matcher matcher;

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getUSSDIN() {
        return USSDIN;
    }

    public void setUSSDIN(String USSDIN) {
        this.USSDIN = USSDIN;
    }

    public String getUssdCode() {
        return ussdCode;
    }

    public void setUssdCode(String ussdCode) {
        this.ussdCode = ussdCode;
    }

    public void redirectTo(String windowName, USSDSession session, USSDResponse response) {

        this.inputValue = null;
        session.setCurrentWindow(windowName);
        session.saveSession();

        USSDResponse ussdResponse =  USSDPlus.executeRequest(application,this,session);
        response.setWindow(ussdResponse.getWindow());
        response.setResponseType(ussdResponse.getResponseType());
        response.setSession(ussdResponse.getSession());

    }

    public void setApplication(USSDApplication application) {
        this.application = application;
    }

    public USSDApplication getApplication() {
        return application;
    }

    public Matcher getInputRegexpMatcher() {

        return matcher;

    }

    public void setInputRegexpMatcher(Matcher matcher) {

        this.matcher = matcher;

    }
}
