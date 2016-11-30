package org.ussdplus;

/**
 * @author Mário Júnior
 */
public abstract class AbstractIdentifiable implements Identifiable {

    private String id;

    public AbstractIdentifiable(String id){

        this.id = id;

    }

    public AbstractIdentifiable(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
