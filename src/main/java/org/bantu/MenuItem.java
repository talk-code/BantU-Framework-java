package org.bantu;

/**
 * @author Mário Júnior
 */
public class MenuItem extends AbstractRenderableItem {

    private String description;
    private String value;
    private String targetWindow;
    private String index;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTargetWindow() {
        return targetWindow;
    }

    public void setTargetWindow(String targetWindow) {
        this.targetWindow = targetWindow;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public static class Builder{

        private MenuItem menuItem = new MenuItem();

        public Builder withValue(String value){

            menuItem.setValue(value);
            return this;

        }

        public Builder withDescription(String description){

            menuItem.setDescription(description);
            return this;

        }

        public Builder withTargetWindow(String window){

            menuItem.setTargetWindow(window);
            return this;

        }

        public Builder withIndex(String index){

            menuItem.index = index;
            return this;

        }

        public MenuItem build(){

            return menuItem;

        }

    }
}
