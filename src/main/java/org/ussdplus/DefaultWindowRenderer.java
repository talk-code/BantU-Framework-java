package org.ussdplus;

import org.ussdplus.*;

/**
 * @author Mário Júnior
 */
public class DefaultWindowRenderer implements WindowRenderer {


    public String render(Window window, USSDResponse response) {


        StringBuilder builder = new StringBuilder();
        for(Message message: window.getMessages()){

            builder.append(message.getContent());
            builder.append("\n");

        }


        for(MenuItem menuItem: window.getMenuItems())
            builder.append(menuItem.getIndex()+"."+menuItem.getDescription()+"\n");


        String preFinal = builder.toString();

        USSDSession session = response.getSession();

        for(Object key:session.keySet()){

            Object value = session.get(key);
            preFinal = preFinal.replaceAll("\\{\\{"+key.toString()+"\\}\\}",value.toString()); //TODO: Fix this

        }

        return preFinal;

    }
}
