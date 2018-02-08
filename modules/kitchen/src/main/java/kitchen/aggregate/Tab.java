package customer.aggregate;

import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import java.util.ArrayList;
import java.util.List;

public class Tab {
    private String name;
    private String timeCreated;
    private String id;
    private List<String> orders= new ArrayList();
    private boolean isOpen;
//    public Tab(String name,String timeCreated, String id) {
//        this.name=name;
//        this.timeCreated=timeCreated;
//        this.id=id;
//        isOpen=true;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public List<String> getList() {
        return orders;
    }

    public void setList(List<String> orders) {
        this.orders = orders;
    }
}
