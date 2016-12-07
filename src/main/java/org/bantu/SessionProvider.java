package org.bantu;

/**
 * @author Mário Júnior
 */
public interface SessionProvider {

    public USSDSession getSession(USSDRequest request);

}
