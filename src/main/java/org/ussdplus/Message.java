package org.ussdplus;

/**
 * @author Mário Júnior
 */
public class Message extends AbstractRenderableItem {

    private String content;

    public Message(){ }


    public Message(String content){

        super();
        this.content = content;

    }

    public Message(String content, String id){

        super(id);
        this.content = content;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
