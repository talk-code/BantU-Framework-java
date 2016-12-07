package org.bantu;

/**
 * @author Mário Júnior
 */
public abstract class AbstractIdentifiable implements Identifiable {

    private String id;

    private String tag;

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

    public void setTag(String tag){this.tag = tag; }

    public String getTag(){return this.tag;}



}
