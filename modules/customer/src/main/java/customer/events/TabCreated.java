package customer.events;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TabCreated implements ParentEvent {
    private String name;
    private String timeCreated;
    private  UUID uuid;
    private EventType eventType;

public TabCreated(){}

//    public TabCreated(String name) {
//        this.name=name;
//        this.uuid = UUID.randomUUID();
//        eventType= EventType.TAB_CREATED;
//    }




    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getTimeCreated() {
        return timeCreated;
    }
    public String toString(){
         return "ID: "+this.getUuid()+"  Time created: "+this.timeCreated;
    }
}
