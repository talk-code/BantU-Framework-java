package org.bantu;

/**
 * @author Mário Júnior
 */
public class WindowNotFoundException extends BantURequestException {

    private String windowName;

    public WindowNotFoundException(String name, USSDRequest request, USSDResponse response, USSDSession session){

        super(String.format("A window with name \"%s\" could not be found.",name),request,response,session);
        this.windowName = name;

    }

    public String getWindowName() {
        return windowName;
    }
}
