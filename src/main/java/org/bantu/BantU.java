package org.bantu;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Mário Júnior
 */
public class BantU {

    private static CoreFilter coreFilter = null;
    private static MenuIndexer menuIndexer;
    private static WindowRenderer windowRenderer = null;

    public static void setMenuIndexer(MenuIndexer enumerator){

        menuIndexer = enumerator;

    }

    protected static MenuIndexer getMenuIndexer(){

        return menuIndexer;

    }

    public static void setWindowRenderer(WindowRenderer windowRenderer){

        BantU.windowRenderer = windowRenderer;

    }

    public static WindowRenderer getWindowRenderer(){

        return windowRenderer;

    }

    static {

        coreFilter = new CoreFilter();
        menuIndexer = new DefaultMenuIndexer();
        windowRenderer = new DefaultWindowRenderer();

    }

    public static USSDResponse executeRequest(USSDApplication application, USSDRequest request){

        return executeRequest(application,request,null);

    }

    protected static USSDResponse executeRequest(USSDApplication application, USSDRequest request, USSDSession session){

        USSDResponse ussdResponse = new BaseUSSDResponse();
        USSDSession ussdSession = session;


        if(ussdSession==null){

            SessionProvider sessionProvider = application.getSessionProvider();
            if(sessionProvider==null)
                throw new IllegalStateException(String.format("The %s instance must not be null",SessionProvider.class.getSimpleName()));

            ussdSession = sessionProvider.getSession(request);
            if(ussdSession==null)
                throw new NullPointerException(String.format("The %s instance returned by %s must not be null",
                        USSDSession.class.getSimpleName(),SessionProvider.class.getSimpleName()));

        }

        request.setApplication(application);
        ussdResponse.setSession(ussdSession);



        //The core filter was not added to the application yet
        if(!application.getFilters().contains(coreFilter))
            application.getFilters().add(coreFilter);


        //Create a chain of all the filters
        USSDFilteringChain chain = createFilteringChain(application);

        //Call the filters chain
        chain.proceed(request,ussdSession,ussdResponse);


        if(request instanceof GetRequest || request instanceof PostRequest) {

            if (ussdResponse.getWindow().isForm())
                ussdResponse.setResponseType(ResponseType.FORM);
            else
                ussdResponse.setResponseType(ResponseType.MESSAGE);

        }else{

            ussdResponse.setResponseType(ResponseType.MESSAGE);

        }



        if(ussdResponse.getResponseType()==ResponseType.FORM){

            //Persist the window in the NavigationCache
            NavigationCache navigationCache = application.getNavigationCache();
            if(navigationCache!=null){

                try {

                    navigationCache.storeWindow(ussdResponse.getWindow(), request, session);

                }catch (Exception ex){

                    throw new WindowStoreFailedException(ussdResponse.getWindow().getId(),request,ussdResponse,session);

                }

            }

        }else{


            //Terminate the session
            ussdSession.close();

        }

        //Return the produced USSDResponse
        return ussdResponse;

    }




    private static USSDFilteringChain createFilteringChain(USSDApplication application){

        final Queue<USSDFilter> filters = new LinkedList<USSDFilter>();
        filters.addAll(application.getFilters());

        USSDFilteringChain chain = new USSDFilteringChain() {


            public void proceed(USSDRequest request, USSDSession session, USSDResponse response) {

                if(filters.size()>0)
                    filters.poll().doFilter(request,session,response, this);

            }

            public void appendFilter(USSDFilter filter) {

                filters.add(filter);

            }
        };

        return chain;

    }

    public static USSDApplication getApplicationFromXML(InputStream inputStream){

        //TODO: Implement
        return null;

    }


}
