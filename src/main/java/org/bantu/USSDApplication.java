package org.bantu;

import java.util.Collection;
import java.util.List;

/**
 * @author Mário Júnior
 */
public interface USSDApplication {

    void addWindowFilter(String windowName, USSDFilter filter);

    void addFilter(USSDFilter filter);

    void addWindow(Window window);

    List<USSDFilter> getWindowFilters(String windowName);

    List<USSDFilter> getFilters();

    void addService(Service service);
    Collection<Service> getServices();
    Service getService(String id);


    String getStartupWindowId();

    void setStartupWindowId(String startupWindowId);

    Window getWindow(String windowId);

    USSDRequest newRequest(String string);
    USSDRequest newTimeoutRequest();
    USSDRequest newReleaseRequest();

    SessionProvider getSessionProvider();
    void setSessionProvider(SessionProvider provider);

    Collection<String> getBaseCodes();
    void activateBaseCode(String code);
    boolean canRun(String code);

    NavigationCache getNavigationCache();
    void setNavigationCache(NavigationCache navigationCache);


}
