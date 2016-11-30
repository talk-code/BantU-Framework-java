package org.ussdplus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mário Júnior
 */
public class BaseUSSDApplication implements USSDApplication {

    private String startupWindowId;

    private Map<String,List<USSDFilter>> windowFilters = new HashMap<String, List<USSDFilter>>();

    private Map<String,Window> windows = new HashMap<String, Window>();

    private List<USSDFilter> filters = new ArrayList<USSDFilter>();


    public void addWindowFilter(String windowName, USSDFilter filter){

        if(windowFilters.containsKey(windowName)) {
            windowFilters.get(windowName).add(filter);
        }else {

            List<USSDFilter> filterList = new ArrayList<USSDFilter>();
            filterList.add(filter);
            windowFilters.put(windowName,filterList);

        }

    }


    public void addFilter(USSDFilter filter){

        filters.add(filter);

    }


    public void addWindow(Window window){

        this.windows.put(window.getId(),window);

    }


    public List<USSDFilter> getWindowFilters(String windowName){

        if(windowFilters.containsKey(windowName))
            return windowFilters.get(windowName);

        return new ArrayList<USSDFilter>();

    }

    public List<USSDFilter> getFilters() {

        return filters;

    }

    public String getStartupWindowId() {
        return startupWindowId;
    }

    public void setStartupWindowId(String startupWindowId) {
        this.startupWindowId = startupWindowId;
    }

    public Window getWindow(String windowId){

        if(windows.containsKey(windowId))
            return windows.get(windowId);

        return null;

    }

}
