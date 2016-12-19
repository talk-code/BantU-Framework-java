package org.bantu;

import java.util.Collection;

/**
 * @author Mário Júnior
 */
public interface MenuItemsProvider {

    /**
     * The implementation of this method should never be based in {@link USSDSession}  or {@link USSDRequest} properties that vary per request.
     * If the implementation of this method relies on such a values then
     * the application must have a {@link NavigationCache}.
     * @param windowName
     * @param request
     * @param session
     * @return
     */
    public Collection<MenuItem> getMenuItems(String windowName,USSDRequest request, USSDSession session);

}
