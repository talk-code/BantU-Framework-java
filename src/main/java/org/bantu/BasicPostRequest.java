package org.bantu;


/**
 * @author Mário Júnior
 */
public class BasicPostRequest extends BasicUSSDRequest implements PostRequest {

    private String inputValue;

    protected BasicPostRequest(){

        super();

    }


    public void redirectTo(String windowName, USSDSession session, USSDResponse response) {
        super.redirectTo(windowName,session,response);

        GetRequest getRequest = buildGetRequest();
        delegateRequest(getRequest,session,response);

    }



    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {

        this.inputValue = inputValue;

    }
}
