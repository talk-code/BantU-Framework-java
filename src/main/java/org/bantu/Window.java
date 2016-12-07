package org.bantu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mário Júnior
 */
public class Window extends AbstractIdentifiable {

    private Header header;
    private Input input;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();
    private String menuValueName;
    private USSDProcessor processor;
    private List<MenuItemsProvider> menuItemProviders = new ArrayList<MenuItemsProvider>();
    private List<Message> messages = new ArrayList<Message>();

    public Window(){}

    public Window(String id){

        super(id);

    }

    public String getMenuValueName() {
        return menuValueName;
    }

    public void setMenuValueName(String menuValueName) {
        this.menuValueName = menuValueName;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public USSDProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(USSDProcessor processor) {
        this.processor = processor;
    }

    public void addMenuItemsProvider(MenuItemsProvider provider){

        this.menuItemProviders.add(provider);

    }

    public List<MenuItemsProvider> getMenuItemProviders(){

        return this.menuItemProviders;

    }

    public void addMessage(Message message){

        this.messages.add(message);

    }

    public void addMenuItem(MenuItem menuItem){

        this.menuItems.add(menuItem);

    }

    public List<Message> getMessages(){

        return this.messages;
    }

    public boolean isForm(){

        return menuItems.size()>0||input!=null;

    }

}
