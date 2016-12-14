package org.bantu;

/**
 * @author Mário Júnior
 */
public class BantUException extends RuntimeException {

    public BantUException(String message){

        super(message);

    }

    public BantUException(String message, Throwable reason){

        super(message,reason);

    }

}
