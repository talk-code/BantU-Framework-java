package org.bantu;

/**
 * @author Mário Júnior
 */
public interface ServiceRequest extends BaseCodeRequest {

    public String getUSSDCode();
    public String getServiceName();
    public String getServiceDescription();

}
