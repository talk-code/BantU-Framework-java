package org.ussdplus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mário Júnior
 */
public class BasicSessionProvider implements SessionProvider {

    private static Map<String,USSDSession> sessions = new HashMap<String, USSDSession>();

    public BasicSessionProvider(){}

    public USSDSession getSession(final USSDRequest request) {

        if(!sessions.containsKey(request.getMSISDN()))
            sessions.put(request.getMSISDN(),new BasicSession(){


                public void saveSession() {

                    sessions.put(request.getMSISDN(),this);

                }
            });

        return sessions.get(request.getMSISDN());

    }

}
