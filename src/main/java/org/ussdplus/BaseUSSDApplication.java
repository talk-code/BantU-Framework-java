package org.ussdplus;

import java.util.*;

/**
 * @author Mário Júnior
 */
public class BaseUSSDApplication implements USSDApplication {

    private String startupWindowId;
    private SessionProvider sessionProvider;

    private Map<String,List<USSDFilter>> windowFilters = new HashMap<String, List<USSDFilter>>();

    private Map<String,Window> windows = new HashMap<String, Window>();

    private List<USSDFilter> filters = new ArrayList<USSDFilter>();

    private Map<String,Boolean> baseCodes = new HashMap<String, Boolean>();

    public BaseUSSDApplication(){

        this.sessionProvider =  new BasicSessionProvider();

    }


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

    public USSDRequest newRequest(String string) {

        if(string==null||string.length()==0)
            throw new IllegalArgumentException("Request cannot be null or null");


        //This is a well known base code
        if(canRun(string.trim())){

            //Its a GetRequest
            BasicGetRequest request = new BasicGetRequest();
            request.setApplication(this);
            request.setUSSDBaseCode(string.trim());
            return request;

        }

        //TODO: Match a service


        //It gotta be a post request
        BasicPostRequest request = new BasicPostRequest();
        request.setApplication(this);
        request.setInputValue(string);
        return request;

    }

    public USSDRequest newTimeoutRequest() {


        //TODO: Create and return a Timeout Request
        return null;

    }

    public USSDRequest newReleaseRequest() {

        //TODO: Create and return a Release Request

        return null;
    }

    public SessionProvider getSessionProvider() {
        return sessionProvider;
    }

    public void setSessionProvider(SessionProvider provider) {
        this.sessionProvider = provider;
    }

    public Collection<String> getBaseCodes() {
        return baseCodes.keySet();
    }

    public void activateBaseCode(String code) {

        baseCodes.put(code,true);

    }

    public boolean canRun(String code) {
        return baseCodes.containsKey(code);
    }

}
