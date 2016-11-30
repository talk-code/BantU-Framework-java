package org.ussdplus;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mário Júnior
 */
public class CoreFilter implements USSDFilter {

    private String currentWindowName;
    private Window currentWindow;

    public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain chain) {

        if(session==null)
            throw new SessionNotInitializedException();


        currentWindowName = session.getCurrentWindow();
        if(currentWindowName==null){

            //Set the current window
            session.setCurrentWindow(request.getApplication().getStartupWindowId());
            currentWindowName = request.getApplication().getStartupWindowId();

        }


        currentWindow = request.getApplication().getWindow(currentWindowName);

        List<USSDFilter> windowFilters =  request.getApplication().getWindowFilters(session.getCurrentWindow());
        if(windowFilters.size()>0){

            //Append each window filters to the filter chain
            for(USSDFilter filter: windowFilters)
                chain.appendFilter(filter);

            //Add this filter to the end of the filters chain
            chain.appendFilter(new USSDFilter() {

                public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                    proceedProcessing(request,session,response);

                }
            });

            chain.proceed(request,session,response);

            return;


        }

        proceedProcessing(request,session,response);

        session.saveSession();//Session will always be persisted


    }

    private void proceedProcessing(USSDRequest request,
                                   USSDSession session, USSDResponse response){

        if(currentWindow==null)
            throw new WindowNotFoundException(currentWindowName);


        //Execute menu providers
        getMenuItemsFromProviders(currentWindow,request,session);

        //Index each of the non indexed menu items
        OrbitUSSD.getMenuIndexer().index(currentWindow.getMenuItems());

        response.setSession(session);
        response.setWindow(currentWindow);


        boolean proceed = true;

        //Input and Menus can only be matched if the request comes with an input, which
        //also means that processors execution is dependent on that
        if(request.getInputValue()!=null) {

            //Match input regular expression and put the value on session or redirect

            proceed = !matchMenuItemsAndRedirect(currentWindow, request, session, response);

            if(proceed){

                if (currentWindow.getInput() != null)
                    proceed = matchInput(currentWindow, request, session, response);

            }

            //Execute the processor
            if(proceed) {

                USSDProcessor processor = currentWindow.getProcessor();
                if (processor != null)
                    processor.process(request, session, response);

            }

        }

        session.saveSession();//Session will always be persisted


    }


    private boolean regularExpressionMatches(String regexp, String value, USSDRequest request){

        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(value);
        request.setInputRegexpMatcher(matcher);
        return matcher.matches();

    }


    private void getMenuItemsFromProviders(Window window, USSDRequest request, USSDSession session){

        List<MenuItemsProvider> menuItemsProviders =  window.getMenuItemProviders();
        for(MenuItemsProvider provider: menuItemsProviders){

            Collection<MenuItem> menuItems = provider.getMenuItems(window.getId(),request,session);
            window.getMenuItems().addAll(menuItems);

        }

    }


    private boolean matchInput(Window currentWindow, USSDRequest request, USSDSession session, USSDResponse response){

        Input input = currentWindow.getInput();
        String value = request.getInputValue();
        session.put(input.getName(),value);

        if(currentWindow.getInput().getRegexp()!=null){

            boolean matches = regularExpressionMatches(input.getRegexp(),value, request);

            if(!matches) {

                request.redirectTo(input.getOnErrorTargetWindow(), session, response);
                return false;

            }

        }


        return true;


    }


    private boolean matchMenuItemsAndRedirect(Window currentWindow,USSDRequest request, USSDSession session, USSDResponse response){

        //Match the menu Items with the request value
        for(MenuItem menuItem: currentWindow.getMenuItems()){

            //Menu Item matched
            if(menuItem.getIndex().equals(request.getInputValue())){

                //Take the menu item's value and put in session
                if(currentWindow.getMenuValueName()!=null)
                    session.put(currentWindow.getMenuValueName(), menuItem.getValue());



                if(menuItem.getTargetWindow()!=null) {

                    request.redirectTo(menuItem.getTargetWindow(), session, response);
                    return true;

                }

                break;

            }

        }

        return false;

    }
}
