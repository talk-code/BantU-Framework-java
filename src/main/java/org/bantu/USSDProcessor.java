package org.bantu;

/**
 * @author Mário Júnior
 */
public interface USSDProcessor {

    public void process(USSDRequest request, USSDSession session, USSDResponse response);

}
