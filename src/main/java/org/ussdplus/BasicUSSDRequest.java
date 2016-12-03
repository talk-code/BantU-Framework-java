package org.ussdplus;

import java.util.regex.Matcher;

/**
 * @author Mário Júnior
 */
public abstract class BasicUSSDRequest implements USSDRequest {

    private String msisdn;
    private String mcc;
    private String lac;
    private String cid;
    private Object attachment;
    private USSDApplication application;

    public String getMSISDN() {

        return msisdn;

    }

    public void setMSISDN(String MSISDN) {

        this.msisdn = MSISDN;

    }

    public String getMCC() {
        return mcc;
    }

    public void setMCC(String value) {

        this.mcc = value;

    }

    public String getCID() {

        return this.cid;

    }

    public void setCID(String value) {

        this.cid = value;

    }

    public String getLAC() {

        return lac;

    }

    public void setLAC(String value) {

        this.lac = value;

    }

    public void setAttachment(Object attachment) {

        this.attachment = attachment;

    }

    public <T> T getAttachment(Class<T> tClass) {

        return (T) attachment;

    }

    public Object getAttachment(){

        return attachment;

    }

    public void setApplication(USSDApplication application) {
        this.application = application;
    }

    public USSDApplication getApplication() {
        return application;
    }


}
