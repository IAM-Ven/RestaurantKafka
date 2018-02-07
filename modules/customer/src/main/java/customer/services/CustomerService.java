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
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

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
        TabCreated event=new TabCreated(command.getTabCreatedRequest().getName());
        Message<TabCreated> message= MessageBuilder.withPayload(event).build();
        System.out.println("servis napravio event i message: "+event.getName()+" i nesta iz message: "+message.getPayload());
        eventProcessor.restaurant().send(message);
    }

    public void createTabClosed(CustomerCommand command){
        TabClosed event=new TabClosed(command.getTabClosedRequest().getTabId());
        Message<TabClosed> message=MessageBuilder.withPayload(event).build();
        System.out.println("servis napravio event i message: "+event.getTabId()+" i nesta iz message: "+message.getPayload());
        eventProcessor.restaurant().send(message);
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_NAME)
    private void on(TabCreated event) {
        System.out.println("listener cuo da ima event i evo nesta iz eventa: "+event.getName());
        if(event.getEventType()== EventType.TAB_CREATED){
            //list.add(new Tab(event.getName(),event.getTimeCreated(),event.getUuid().toString()));
            Tab tab=new Tab();
            tab.setName(event.getName());
            tab.setTimeCreated(event.getTimeCreated());
            tab.setId(event.getUuid().toString());
            list.add(tab);
        }
        }
    @StreamListener(Channel.RESTAURANT_CHANNEL_NAME)
    private void on(TabClosed event) {
        System.out.println("listener cuo da ima event i evo nesta iz eventa: "+event.getTabId());
        if(event.getEventType()== EventType.TAB_CLOSED){
            list.stream().forEach(i->{
                if(i.getId()==event.getTabId())
                {
                    i.setOpen(false);
                }
            });
        }
    }

}
