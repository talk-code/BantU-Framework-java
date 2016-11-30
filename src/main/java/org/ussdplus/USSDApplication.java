package org.ussdplus;

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

    String getStartupWindowId();

    void setStartupWindowId(String startupWindowId);

    Window getWindow(String windowId);


}
