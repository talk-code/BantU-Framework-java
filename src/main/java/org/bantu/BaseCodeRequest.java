package org.bantu;

/**
 * @author Mário Júnior
 */
public interface BaseCodeRequest extends USSDRequest {

    public String getUSSDBaseCode();
    void setUSSDBaseCode(String code);

}
