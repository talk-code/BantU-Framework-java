package org.bantu;

/**
 * @author Mário Júnior
 */
public class AbstractRenderableItem extends AbstractIdentifiable implements RenderableItem {

    private boolean render = true;


    public AbstractRenderableItem(String id){

        super(id);

    }

    public AbstractRenderableItem(){super();}

    public void setRender(boolean value) {

        this.render = value;

    }

    public boolean isHidden() {

        return !render;

    }

    public void hide() {

        this.render = false;

    }

    public void show() {

        this.render = true;

    }
}
