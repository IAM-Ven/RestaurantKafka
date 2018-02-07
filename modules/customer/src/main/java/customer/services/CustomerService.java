package customer.services;

import customer.aggregate.Tab;
import customer.channelProperties.Channel;
import customer.command.CustomerCommand;
import customer.events.EventProcessor;
import customer.events.EventType;
import customer.events.TabClosed;
import customer.events.TabCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static List<Tab> list= new ArrayList();
    private EventProcessor eventProcessor;
    @Autowired
    public CustomerService(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public List<Tab>getAll(){
        return list;
    }

    public void createTabCreated(CustomerCommand command){
        TabCreated event=new TabCreated();
        event.setName(command.getTabCreatedRequest().getName());
        event.setUuid(UUID.randomUUID());
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setEventType(EventType.TAB_CREATED);
        Message<TabCreated> message= MessageBuilder.withPayload(event).build();
        System.out.println("servis napravio event i message: "+event.getName()+" i nesta iz message: "+message.getPayload());
        eventProcessor.restaurant().send(message);
    }

    public void createTabClosed(CustomerCommand command){
        TabClosed event=new TabClosed();
        event.setTabId(command.getTabClosedRequest().getTabId());
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setEventType(EventType.TAB_CLOSED);
        Message<TabClosed> message=MessageBuilder.withPayload(event).build();
        System.out.println("servis napravio event i message: "+event.getTabId()+" i nesta iz message: "+message.getPayload());
        eventProcessor.restaurant().send(message);
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(TabCreated event) {
        System.out.println("listener");
        System.out.println("listener cuo da ima event i evo nesta iz eventa: "+event.getName());
        if(event.getEventType()== EventType.TAB_CREATED){
            //list.add(new Tab(event.getName(),event.getTimeCreated(),event.getUuid().toString()));
            Tab tab=new Tab();
            tab.setName(event.getName());
            tab.setTimeCreated(event.getTimeCreated());
            tab.setId(event.getUuid().toString());
            tab.setOpen(true);
            list.add(tab);
        }
        }

    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(TabClosed event) {
        System.out.println("listener cuo da ima event i evo nesta iz eventa: "+event.getTabId());
        if(event.getEventType()== EventType.TAB_CLOSED){
//            list.stream().forEach(i->{
//                if(i.getId()==event.getTabId())
//                {
//                    i.setOpen(false);
//                }
//            });
            for(int i=0;i<list.size();i++)
            {
                if(list.get(i).getId().equals(event.getTabId())){
                    list.get(i).setOpen(false);
                }
            }
        }
    }

}
