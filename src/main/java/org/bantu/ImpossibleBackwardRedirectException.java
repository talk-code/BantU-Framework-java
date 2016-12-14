package org.bantu;

/**
 * @author Mário Júnior
 */
public class ImpossibleBackwardRedirectException extends BantURequestException {


    public ImpossibleBackwardRedirectException(MenuItem menuItem, USSDRequest request, USSDResponse response, USSDSession session){

        super(String.format("Session has no valid previousWindow value. User cant be redirected backward by %s Menu Item.",
                menuItem.getId()),request,response,session);

    }

}
