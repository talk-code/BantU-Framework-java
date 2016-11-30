package org.ussdplus;

import org.emerjoin.orbitussd.*;

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
        for(String key:session.keySet()){

            Object value = session.get(key);
            preFinal = preFinal.replaceAll("\\{\\{"+key+"\\}\\}",value.toString()); //TODO: Fix this

        }

        return preFinal;

    }
}
