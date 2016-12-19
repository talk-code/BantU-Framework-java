package org.bantu;

/**
 * @author Mário Júnior
 */
public class WindowStoreFailedException extends BantURequestException {


    public WindowStoreFailedException(String windowId, USSDRequest request, USSDResponse response, USSDSession session) {
        super(String.format("Failed to store window with Id {%s} in NavigationCache",windowId),request,response,session);
    }
}
