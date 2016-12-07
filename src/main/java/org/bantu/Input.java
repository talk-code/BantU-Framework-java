package org.bantu;

/**
 * @author Mário Júnior
 */
public class Input {

    private String regexp;
    private String name;
    private String onErrorTargetWindow;

    public String getRegexp() {
        return regexp;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnErrorTargetWindow() {
        return onErrorTargetWindow;
    }

    public void setOnErrorTargetWindow(String onErrorTargetWindow) {
        this.onErrorTargetWindow = onErrorTargetWindow;
    }

    public static class Builder{

        private Input input = new Input();

        public Builder withName(String name){

            input.setName(name);
            return this;

        }

        public Builder withRegExp(String regExp, String onErrorWindow){

            input.setRegexp(regExp);
            input.setOnErrorTargetWindow(onErrorWindow);
            return this;

        }

        public Input build(){

            return input;

        }


    }


}
