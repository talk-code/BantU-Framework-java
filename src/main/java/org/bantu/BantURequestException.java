package org.bantu;

/**
 * @author Mário Júnior
 */
public class BantURequestException extends BantUException {

    private USSDRequest request;
    private USSDResponse response;
    private USSDSession session;

    public BantURequestException(String message, USSDRequest request, USSDResponse response, USSDSession session){

        super(message);
        this.request = request;
        this.response = response;
        this.session = session;
    }

    public BantURequestException(String message, USSDRequest request, USSDResponse response, USSDSession session, Throwable cause){

        super(message,cause);
        this.request = request;
        this.response = response;
        this.session = session;
    }

}
