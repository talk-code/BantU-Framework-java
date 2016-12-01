package org.ussdplus;

/**
 * @author Mário Júnior
 */
public interface RenderableItem {

    public void setRender(boolean value);
    public boolean isHidden();
    public void hide();
    public void show();

}
