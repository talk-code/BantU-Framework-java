package org.bantu;

/**
 * @author Mário Júnior
 */
public interface PostRequest extends USSDRequest {

    String getInputValue();

    void setInputValue(String inputValue);

}
