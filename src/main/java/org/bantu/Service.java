package org.bantu;

/**
 * @author Mário Júnior
 */
public class Service {

    private String id;
    private String description;
    private String matchPattern;
    private USSDProcessor processor;


    public Service(){



    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMatchPattern() {
        return matchPattern;
    }

    public void setMatchPattern(String matchPattern) {
        this.matchPattern = matchPattern;
    }

    public USSDProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(USSDProcessor processor) {
        this.processor = processor;
    }

    public String getRegularExpression(){

        //TODO: Implement
        return null;

    }

}
