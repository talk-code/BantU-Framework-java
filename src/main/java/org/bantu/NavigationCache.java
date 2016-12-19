package org.bantu;

/**
 * @author Mário Júnior
 */
public interface NavigationCache {

    public void storeWindow(Window window, USSDRequest request, USSDSession session);
    public Window fetchWindow(String windowId,USSDRequest request, USSDSession session);

}
