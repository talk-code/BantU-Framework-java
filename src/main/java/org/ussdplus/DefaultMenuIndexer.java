package org.ussdplus;

import java.util.Collection;

/**
 * @author Mário Júnior
 */
public class DefaultMenuIndexer implements MenuIndexer {

    public void index(Collection<MenuItem> menus) {

        int i=1;
        for(MenuItem menuItem: menus){

            if(menuItem.getIndex()==null)
                menuItem.setIndex(String.valueOf(i++));

        }

    }
}
