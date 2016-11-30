package org.ussdplus;

/**
 * @author Mário Júnior
 */
public class SessionNotInitializedException extends USSDPlusException {

    public SessionNotInitializedException(){

        super(String.format("A %s instance must be injected by the root %s of the application",
                USSDSession.class.getSimpleName(), USSDFilter.class.getSimpleName()));

    }

}
