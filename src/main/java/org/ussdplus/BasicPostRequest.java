package org.ussdplus;


/**
 * @author Mário Júnior
 */
public class BasicPostRequest extends BasicUSSDRequest implements PostRequest {

    private String inputValue;

    protected BasicPostRequest(){

        super();

    }


    public void redirectTo(String windowName, USSDSession session, USSDResponse response) {

        session.setCurrentWindow(windowName);
        session.saveSession();

        GetRequest getRequest = new BasicGetRequest();
        getRequest.setMSISDN(getMSISDN());
        getRequest.setApplication(getApplication());
        getRequest.setAttachment(getAttachment());
        getRequest.setCID(getCID());
        getRequest.setLAC(getLAC());
        getRequest.setMCC(getMCC());

        USSDResponse ussdResponse =  USSDPlus.executeRequest(getApplication(),getRequest,session);
        response.setWindow(ussdResponse.getWindow());
        response.setResponseType(ussdResponse.getResponseType());
        response.setSession(ussdResponse.getSession());

    }



    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {

        this.inputValue = inputValue;

    }
}
