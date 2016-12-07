package org.bantu;

/**
 * @author Mário Júnior
 */
public interface USSDFilter {

    public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution);

}
