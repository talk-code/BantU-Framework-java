package org.bantu;

/**
 * @author Mário Júnior
 */
public class WindowFetchFailedException extends BantURequestException {


    public WindowFetchFailedException(String windowId, USSDRequest request, USSDResponse response, USSDSession session) {
        super(String.format("Failed to fetch window with Id {%s} from NavigationCache",windowId),request,response,session);
    }
}
