package org.bantu;

/**
 * @author Mário Júnior
 */
public class BaseUSSDResponse implements USSDResponse {

    private Window window;
    private ResponseType responseType;
    private USSDSession session;
    private String status = USSDResponse.OK_STATUS;
    private String responseBody;

    protected BaseUSSDResponse(){

        super();

    }

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

    public void setResponseBody(String body) {

        this.responseBody = body;

    }

    public String getResponseBody() {

        return this.responseBody;
    }

    public USSDSession getSession() {
        return session;
    }

    public void setSession(USSDSession session) {
        this.session = session;
    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String value) {

        if(value==null)
            throw new IllegalArgumentException("Status should never be null");
        this.status = value;

    }

    public String toString(){

        //Response window override
        if(responseBody!=null)
            return responseBody;

        if(getWindow()!=null)
            return BantU.getWindowRenderer().render(getWindow(),this);

        //This will avoid null pointer exception when debugger tries to exhibit a string representation for the object
        return "";

    }



    public boolean requestFailed(){

        return this.status.equals(USSDResponse.ERROR_STATUS);

    }

}
