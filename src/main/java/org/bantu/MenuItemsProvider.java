package org.bantu;

import java.util.Collection;

/**
 * @author Mário Júnior
 */
public interface MenuItemsProvider {

    public Collection<MenuItem> getMenuItems(String windowName,USSDRequest request, USSDSession session);

}
