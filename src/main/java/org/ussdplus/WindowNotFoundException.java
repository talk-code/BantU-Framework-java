package org.ussdplus;

/**
 * @author Mário Júnior
 */
public class WindowNotFoundException extends USSDPlusException {

    private String windowName;

    public WindowNotFoundException(String name){

        super(String.format("A window with name \"%s\" could not be found.",name));
        this.windowName = name;

    }

    public String getWindowName() {
        return windowName;
    }
}
