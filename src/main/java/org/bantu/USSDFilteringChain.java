package org.bantu;

/**
 * @author Mário Júnior
 */
public interface USSDFilteringChain {

    public void proceed(USSDRequest request, USSDSession USSDSession, USSDResponse response);
    public void appendFilter(USSDFilter filter);

}
