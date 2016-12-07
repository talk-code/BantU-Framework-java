package org.bantu;

/**
 * @author Mário Júnior
 */
public class BasicServiceRequest extends BasicUSSDRequest implements ServiceRequest {

    private String serviceName;
    private String serviceDescription;
    private String ussdCode;
    private String ussdBaseCode;

    protected BasicServiceRequest(){

        super();

    }

    protected void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    protected void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    protected void setUssdCode(String ussdCode) {
        this.ussdCode = ussdCode;
    }


    public String getUSSDCode() {
        return ussdCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public String getUSSDBaseCode() {
        return ussdBaseCode;
    }

    public void setUSSDBaseCode(String code) {

        this.ussdBaseCode = code;

    }

    public void redirectTo(String windowName, USSDSession session, USSDResponse response) {

        throw new UnsupportedOperationException();

    }
}
