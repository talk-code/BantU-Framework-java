import org.junit.Before;
import org.bantu.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Mário Júnior
 */
public class BasicApplicationTests {

    private static final String TEST_BASE_CODE ="*181#";

    private static USSDApplication getTestApp1(){

        USSDApplication application = new BaseUSSDApplication();

        //Login window
        Window loginWindow = new Window("login");
        loginWindow.addMessage(new Message("Please introduce your pin code","msg1"));
        loginWindow.addMenuItem(new MenuItem.Builder()
                .withDescription("Forgot password")
                .withTargetWindow("recoverPassword")
                .build());
        loginWindow.setInput(new Input.Builder().withName("pin").build());
        loginWindow.setProcessor(new USSDProcessor() {

            public void process(USSDRequest request, USSDSession session, USSDResponse response) {

                String pin = session.get("pin").toString();
                if(pin.equals("1234"))
                    request.redirectTo("operations", session, response);
                else
                    request.redirectTo("recoverPassword", session, response);





            }

        });

        //Recover password
        Window recoverPasswordWindow = new Window("recoverPassword");
        recoverPasswordWindow.addMessage(new Message("Ops! This is not working yet"));


        //Operations window
        Window operationsWindow = new Window("operations");
        operationsWindow.addMessage(new Message("Please select an operation","msg2"));
        operationsWindow.addMenuItem(new MenuItem.Builder()
                .withDescription("Transferences")
                .withTargetWindow("transferences")
                .build());
        operationsWindow.addMenuItem(new MenuItem.Builder()
                .withDescription("Withdrawal")
                .withTargetWindow("withdrawal")
                .build());



        //Transference window
        Window transferencesWindow = new Window("transferences");
        transferencesWindow.addMessage(new Message("Please select the origin account","msg3"));
        transferencesWindow.addMenuItemsProvider(new MenuItemsProvider() {

            public Collection<MenuItem> getMenuItems(String windowName, USSDRequest request, USSDSession session) {

                List<MenuItem> menuItemList = new ArrayList();
                menuItemList.add(new MenuItem.Builder()
                        .withDescription("3400231")
                        .withTargetWindow("amountWindow")
                        .build());


                menuItemList.add(new MenuItem.Builder()
                        .withDescription("171021")
                        .withTargetWindow("amountWindow")
                        .build());

                return menuItemList;


            }
        });




        //Amount window
        Window amountWindow = new Window("amountWindow");
        amountWindow.addMessage(new Message("Please introduce the amount"));
        amountWindow.setInput(new Input.Builder()
                .withName("amount")
                .withRegExp("[0-9]+","invalidAmount")
                .build());
        amountWindow.setProcessor(new USSDProcessor() {

            public void process(USSDRequest request, USSDSession session, USSDResponse response) {

                request.redirectTo("requestSubmitted",session,response);

            }
        });

        Window invalidAmountWindow = new Window("invalidAmount");
        invalidAmountWindow.addMessage(new Message("Invalid Amount : {{amount}}. Please try again"));


        Window requestSubmittedWindow = new Window("requestSubmitted");
        requestSubmittedWindow.addMessage(new Message("Request submitted successfully"));

        application.addWindow(loginWindow);
        application.addWindow(recoverPasswordWindow);
        application.addWindow(operationsWindow);
        application.addWindow(transferencesWindow);
        application.addWindow(amountWindow);
        application.addWindow(invalidAmountWindow);
        application.addWindow(requestSubmittedWindow);
        application.addWindow(recoverPasswordWindow);
        application.activateBaseCode(TEST_BASE_CODE);


        application.setStartupWindowId("login");
        return application;

    }



    @Before
    public void wipeSession(){

        USSDRequest request = getTestApp1().newRequest(TEST_BASE_CODE);
        fillRequestAndGetSession(request,getTestApp1()).close();

    }

    @Test
    public void mustRenderLoginWindow(){


        USSDRequest request = getTestApp1().newRequest(TEST_BASE_CODE);
        fillRequest(request);
        USSDResponse response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("login",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());

    }



    @Test
    public void mustRenderPasswordRecoverWindow(){

        USSDRequest request = getTestApp1().newRequest("1111");

        USSDApplication app = getTestApp1();
        fillRequestAndGetSession(request,app).setCurrentWindow("login");

        USSDResponse response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("recoverPassword",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }




    @Test
    public void mustRenderOperationsWindow(){

        USSDRequest request = getTestApp1().newRequest("1234");
        fillRequest(request);
        USSDResponse response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());

    }



    @Test
    public void mustRenderInvalidAmountWindow(){

        USSDApplication app = getTestApp1();
        USSDRequest request = app.newRequest("1234");
        fillRequestAndGetSession(request,app).setCurrentWindow("login");

        USSDResponse response = USSDPlus.executeRequest(app,request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("1");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("transferences",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("2");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("amountWindow",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("-1000");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("invalidAmount",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }


    @Test
    public void mustRenderRequestSubmittedWindow(){

        USSDApplication app = getTestApp1();
        USSDRequest request = app.newRequest("1234");
        fillRequest(request);

        USSDResponse response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("1");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("transferences",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("2");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("amountWindow",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request = app.newRequest("1000");
        fillRequest(request);

        response = USSDPlus.executeRequest(getTestApp1(),request);
        assertEquals("requestSubmitted",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }


    @Test
    public void windowFilterMustBeInvoked(){

        USSDApplication application = new BaseUSSDApplication();
        Window window = new Window("startup");
        window.addMessage(new Message("Welcome, please type something"));
        window.setInput(new Input.Builder().withName("something").build());

        application.addWindowFilter("startup", new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain chain) {

                chain.proceed(request,session,response);
                assertEquals("Added by the window filter",response.getWindow().getMessages().get(0).getContent());
                response.getWindow().getMessages().get(0).setContent("Modified by another window filter");

            }
        });

        application.addWindowFilter("startup", new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain chain) {

                chain.proceed(request,session,response);
                response.getWindow().getMessages().get(0).setContent("Added by the window filter");

            }

        });

        application.addWindow(window);
        application.setStartupWindowId("startup");


        USSDRequest request = application.newRequest(TEST_BASE_CODE);
        fillRequest(request);
        USSDResponse response = USSDPlus.executeRequest(application,request);
        assertEquals("Modified by another window filter",response.getWindow().getMessages().get(0).getContent());


    }




    @Test
    public void globalFilterMustBeInvoked(){

        USSDApplication application = new BaseUSSDApplication();
        Window mainWindow = new Window("main");
        mainWindow.addMessage(new Message("I'm the main window"));
        mainWindow.setInput(new Input.Builder().withName("something").build());

        Window secondWindow = new Window("second");
        secondWindow.addMessage(new Message("I'm the second window"));

        application.addFilter(new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                session.setCurrentWindow("second");
                execution.proceed(request,session,response);

            }
        });

        application.addFilter(new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                execution.proceed(request,session,response);
                response.getWindow().getMessages().get(0).setContent("I'm the third window");

            }
        });

        application.addWindow(mainWindow);
        application.addWindow(secondWindow);
        application.setStartupWindowId("main");

        USSDRequest request = application.newRequest(TEST_BASE_CODE);
        fillRequest(request);
        USSDResponse response = USSDPlus.executeRequest(application,request);
        assertEquals("I'm the third window",response.getWindow().getMessages().get(0).getContent());

    }


    @Test(expected = WindowNotFoundException.class)
    public void exceptionMustBeThrownIfTargetWindowOfMenuCouldNotBeFound(){

        USSDApplication application = new BaseUSSDApplication();
        Window mainWindow = new Window("main");
        mainWindow.addMessage(new Message("Hello. Please select an option"));
        mainWindow.addMenuItem(
                new MenuItem.Builder().withDescription("First")
                .withTargetWindow("future-window")
                .build());

        mainWindow.addMenuItem(
                new MenuItem.Builder().withDescription("Second")
                        .withTargetWindow("present-window")
                        .build());


        application.addWindow(mainWindow);
        application.setStartupWindowId("main");

        USSDRequest request = application.newRequest("1");
        fillRequest(request);
        USSDPlus.executeRequest(application,request);


    }

    @Test(expected = WindowNotFoundException.class)
    public void exceptionMustBeThrownIfStarupWindowCouldNotBeFound(){

        USSDApplication application = new BaseUSSDApplication();
        Window mainWindow = new Window("main");
        mainWindow.addMessage(new Message("This wont be rendered"));

        application.addWindow(mainWindow);
        application.setStartupWindowId("maniac");

        USSDRequest request = application.newRequest(TEST_BASE_CODE);
        fillRequest(request);
        USSDPlus.executeRequest(application,request);


    }



    @Test
    public void regExpMustFailAndRequestRedirectToErrorWindow(){

        USSDApplication application = new BaseUSSDApplication();
        Window mainWindow = new Window("main");
        mainWindow.addMessage(new Message("Please type a value that matches the regular expression"));
        mainWindow.setInput(new Input.Builder().withName("age").withRegExp("[0-9]","error-window").build());


        Window errorWindow = new Window("error-window");
        errorWindow.addMessage(new Message("Regular expression failed","error"));


        application.addWindow(mainWindow);
        application.addWindow(errorWindow);

        application.setStartupWindowId("main");

        USSDRequest request = application.newRequest("ab");
        fillRequest(request);

        USSDResponse response = USSDPlus.executeRequest(application,request);
        assertEquals("error-window",response.getWindow().getId());
        assertEquals("error",response.getWindow().getMessages().get(0).getId());

    }



    @Test
    public void regExpMustSucceedAndValueMustBeSavedInSession(){

        USSDApplication application = new BaseUSSDApplication();
        Window mainWindow = new Window("main");
        mainWindow.addMessage(new Message("Please type a value that matches the regular expression"));
        mainWindow.setInput(new Input.Builder().withName("age").withRegExp("[0-9]","error-window").build());

        application.addWindow(mainWindow);
        application.setStartupWindowId("main");

        USSDRequest request = application.newRequest("9");
        fillRequest(request);

        USSDResponse response = USSDPlus.executeRequest(application,request);
        assertTrue(response.getSession().containsKey("age"));
        assertEquals("9",response.getSession().get("age").toString());

    }


    @Test
    public void inputValueWithoutRegExpMustBeFoundInSession(){



    }






    @Test
    public void messageMustBeRenderedWithSessionValues(){



    }

    private void fillRequest(USSDRequest request){

        request.setMSISDN("+258842538083");
        //TODO: Set more stuff


    }

    private USSDSession fillRequestAndGetSession(USSDRequest request, USSDApplication app){

        fillRequest(request);
        return app.getSessionProvider().getSession(request);

    }



}
