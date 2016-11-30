package org.ussdplus;

/**
 * @author Mário Júnior
 */
public class USSDPlusException extends RuntimeException {

    public USSDPlusException(String message){

        super(message);

    }

    public USSDPlusException(String message, Throwable reason){

        super(message,reason);

    }

}
