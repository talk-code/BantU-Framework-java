package org.bantu;

/**
 * @author Mário Júnior
 */
public class BasicGetRequest extends BasicUSSDRequest implements GetRequest {

    private String baseUssdCode;


    protected BasicGetRequest(){

        super();

    }

    public void redirectTo(String windowName, USSDSession session, USSDResponse response) {

        session.setCurrentWindow(windowName);
        session.saveSession();

        GetRequest getRequest = new BasicGetRequest();
        getRequest.setMSISDN(getMSISDN());
        getRequest.setUSSDBaseCode(getUSSDBaseCode());
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

    public String getUSSDBaseCode() {

        return baseUssdCode;
    }

    public void setUSSDBaseCode(String code) {

        this.baseUssdCode = code;

    }
}
