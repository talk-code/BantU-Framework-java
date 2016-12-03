package org.ussdplus;

import java.util.regex.Matcher;

/**
 * @author Mário Júnior
 */
public interface PostRequest extends USSDRequest {

    String getInputValue();

    void setInputValue(String inputValue);

}
