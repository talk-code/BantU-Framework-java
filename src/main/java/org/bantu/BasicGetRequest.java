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
        super.redirectTo(windowName,session,response);

        GetRequest getRequest = buildGetRequest();
        getRequest.setUSSDBaseCode(getUSSDBaseCode());

        delegateRequest(getRequest,session,response);

    }

    public String getUSSDBaseCode() {

        return baseUssdCode;
    }

    public void setUSSDBaseCode(String code) {

        this.baseUssdCode = code;

    }
}
