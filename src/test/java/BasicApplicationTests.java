import org.emerjoin.orbitussd.*;
import org.ussdplus.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Mário Júnior
 */
public class BasicApplicationTests {

    private static USSDSession ussdSession = new BaseUSSDSession();

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

        application.addFilter(new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                if(session==null) {
                    execution.proceed(request, ussdSession, response);
                }else {
                    execution.proceed(request, session, response);
                }

            }
        });

        application.setStartupWindowId("login");
        return application;

    }


    @Test
    public void mustRenderLoginWindow(){

        ussdSession = new BaseUSSDSession();
        USSDRequest request = new BaseUSSDRequest();
        USSDResponse response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("login",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());

    }

    @Test
    public void mustRenderPasswordRecoverWindow(){

        ussdSession.setCurrentWindow("login");
        USSDRequest request = new BaseUSSDRequest();
        request.setInputValue("1111");

        USSDResponse response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("recoverPassword",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }


    @Test
    public void mustRenderOperationsWindow(){

        ussdSession.setCurrentWindow("login");
        USSDRequest request = new BaseUSSDRequest();
        request.setInputValue("1234");

        USSDResponse response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());

    }


    @Test
    public void mustRenderInvalidAmountWindow(){

        ussdSession.setCurrentWindow("login");
        USSDRequest request = new BaseUSSDRequest();
        request.setInputValue("1234");

        USSDResponse response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("1");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("transferences",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("2");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("amountWindow",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("-1000");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("invalidAmount",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }


    @Test
    public void mustRenderRequestSubmittedWindow(){

        ussdSession.setCurrentWindow(null);
        USSDRequest request = new BaseUSSDRequest();
        request.setInputValue("1234");

        USSDResponse response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("operations",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("1");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("transferences",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("2");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("amountWindow",response.getWindow().getId());
        assertEquals(ResponseType.FORM,response.getResponseType());
        request.setInputValue("1000");

        response = OrbitUSSD.executeRequest(getTestApp1(),request);
        assertEquals("requestSubmitted",response.getWindow().getId());
        assertEquals(ResponseType.MESSAGE,response.getResponseType());

    }


    @Test
    public void windowFilterMustBeInvoked(){

        USSDApplication application = new BaseUSSDApplication();
        Window window = new Window("startup");
        window.addMessage(new Message("Welcome, please type something"));
        window.setInput(new Input.Builder().withName("something").build());

        application.addFilter(new USSDFilter() {

            public void doFilter(USSDRequest request, USSDSession session, USSDResponse response, USSDFilteringChain execution) {

                execution.proceed(request,new BaseUSSDSession(),response);

            }
        });

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
                response.getWindow().addMessage(new Message("Added by the window filter"));

            }

        });

        application.setStartupWindowId("startup");


        USSDRequest request = new BaseUSSDRequest();
        USSDResponse response = OrbitUSSD.executeRequest(application,request);
        assertEquals("Modified by another window filter",response.getWindow().getMessages().get(0).getContent());



    }


    @Test
    public void globalFilterMustBeInvoked(){



    }


    @Test
    public void targetWindowMustNotBeFound(){



    }


    @Test
    public void regexpMustFailAndRequestRedirectToErrorWindow(){



    }


    @Test
    public void inputValueMustBeFoundInSession(){



    }



}
