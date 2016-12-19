package org.bantu;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mário Júnior
 */
public class CoreFilter implements USSDFilter {

    private static final String BACKWARD_TARGET_WINDOW="#backward";

    private enum  WindowSource {

        NAVIGATION_CACHE,
        APPLICATION_INSTANCE

    };

    private class WindowResult{

        public Window window;
        public WindowSource source;

    }


    public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain chain) {


        String cWName;
        final Window currentWindow;

        cWName = session.getCurrentWindow();
        if(cWName==null){

            //Set the current window
            session.setCurrentWindow(request.getApplication().getStartupWindowId());
            cWName = request.getApplication().getStartupWindowId();

        }


        final String currentWindowName = cWName;
        WindowResult result = getWindow(currentWindowName,request,session,response);
        currentWindow =  result.window;


        //Window was restored from navigation cache
        if(result.source==WindowSource.NAVIGATION_CACHE){

            proceedProcessing(request,session,response, currentWindowName, currentWindow);
            return;

        }

        //WINDOW WAS FETCHED DIRECTLY FROM APPLICATION INSTANCE

        //Execute menu Items providers
        getMenuItemsFromProviders(currentWindow,request,session);

        List<USSDFilter> windowFilters =  request.getApplication().getWindowFilters(session.getCurrentWindow());
        if(windowFilters.size()>0){

            //Append each window filters to the filter chain
            for(USSDFilter filter: windowFilters)
                chain.appendFilter(filter);

            //Add this filter to the end of the filters chain
            chain.appendFilter(new USSDFilter() {

                public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                    //Index each of the non indexed menu items
                    BantU.getMenuIndexer().index(currentWindow.getMenuItems());
                    proceedProcessing(request,session,response,currentWindowName,currentWindow);

                }
            });

            chain.proceed(request,session,response);

            return;


        }

        //Index each of the non indexed menu items
        BantU.getMenuIndexer().index(currentWindow.getMenuItems());
        proceedProcessing(request,session,response, currentWindowName, currentWindow);

    }


    private WindowResult getWindow(String name,USSDRequest request,
                                   USSDSession session, USSDResponse response){

        WindowResult windowResult = new WindowResult();

        if(request instanceof PostRequest) {

            NavigationCache navigationCache = request.getApplication().getNavigationCache();
            if (navigationCache != null) {

                try {

                    windowResult.window = navigationCache.fetchWindow(name, request, session);
                    windowResult.source = WindowSource.NAVIGATION_CACHE;

                } catch (Exception ex) {

                    throw new WindowFetchFailedException(name, request, response, session);

                }

            }
        }

        if(windowResult.window==null) {

            windowResult.window = request.getApplication().getWindow(name);
            windowResult.source = WindowSource.APPLICATION_INSTANCE;

        }

        if(windowResult.window==null)
            throw new WindowNotFoundException(name,request,response,session);

        return windowResult;

    }

    private void proceedProcessing(USSDRequest request,
                                   USSDSession session, USSDResponse response,
                                   String currentWindowName,Window currentWindow){

        if(currentWindow==null)
            throw new WindowNotFoundException(currentWindowName,request,response,session);


        //response.setSession(session);
        response.setWindow(currentWindow);


        boolean proceed = true;

        //Input and Menus can only be matched if the request comes with an input, which
        //also means that processors execution is dependent on that
        if(request instanceof PostRequest) {

            PostRequest postRequest = (PostRequest) request;

            if (postRequest.getInputValue() != null) {

                //Match input regular expression and put the value on session or redirect
                proceed = !matchMenuItemsAndRedirect(currentWindow, postRequest, session, response);

                if(proceed){

                    if (currentWindow.getInput() != null)
                        proceed = matchInput(currentWindow, postRequest, session, response);

                }


                //Execute the processor
                if (proceed) {

                    USSDProcessor processor = currentWindow.getProcessor();
                    if (processor != null)
                        processor.process(request, session, response);

                }


            }

        }


        session.saveSession();//Session will always be persisted


    }


    private boolean regularExpressionMatches(String regexp, String value, USSDRequest request){

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();

    }


    private void getMenuItemsFromProviders(Window window, USSDRequest request, USSDSession session){

        List<MenuItemsProvider> menuItemsProviders =  window.getMenuItemProviders();
        for(MenuItemsProvider provider: menuItemsProviders){

            Collection<MenuItem> menuItems = provider.getMenuItems(window.getId(),request,session);
            window.getMenuItems().addAll(menuItems);

        }

    }


    private boolean matchInput(Window currentWindow, PostRequest request, USSDSession session, USSDResponse response){

        Input input = currentWindow.getInput();
        String value = request.getInputValue();
        session.put(input.getName(),value);

        session.saveSession();


        if(currentWindow.getInput().getRegexp()!=null){

            boolean matches = regularExpressionMatches(input.getRegexp(),value, request);

            if(!matches) {

                request.redirectTo(input.getOnErrorTargetWindow(), session, response);
                return false;

            }

        }


        return true;


    }


    private boolean matchMenuItemsAndRedirect(Window currentWindow,PostRequest request, USSDSession session, USSDResponse response){

        //Match the menu Items with the request value
        for(MenuItem menuItem: currentWindow.getMenuItems()){

            //Menu Item matched
            if(menuItem.getIndex().equals(request.getInputValue())){

                //Take the menu item's value and put in session
                if(currentWindow.getMenuValueName()!=null) {

                    session.put(currentWindow.getMenuValueName(), menuItem.getValue());
                    session.saveSession();


                }


                if(menuItem.getTargetWindow()!=null) {

                    String windowId = whereToGo(menuItem,request,response,session);
                    request.redirectTo(windowId, session, response);

                    return true;

                }

                break;

            }

        }

        return false;

    }


    private String  whereToGo(MenuItem menuItem,USSDRequest request, USSDResponse response, USSDSession session){

        if(menuItem.getTargetWindow().equals(BACKWARD_TARGET_WINDOW)){

            if(session.getPreviousWindow()==null)
                throw new ImpossibleBackwardRedirectException(menuItem,request,response,session);

            return session.getPreviousWindow();


        }

        return menuItem.getTargetWindow();

    }




}
