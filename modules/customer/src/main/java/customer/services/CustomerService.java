package customer.services;

import customer.aggregate.Tab;
import customer.channelProperties.Channel;
import customer.command.CustomerCommand;
import customer.events.*;
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
        eventProcessor.restaurant().send(message);
    }

    public void createTabClosed(CustomerCommand command){
        TabClosed event=new TabClosed();
        event.setTabId(command.getTabClosedRequest().getTabId());
        event.setUuid(UUID.randomUUID());
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setEventType(EventType.TAB_CLOSED);
        Message<TabClosed> message=MessageBuilder.withPayload(event).build();
        eventProcessor.restaurant().send(message);
    }
    public void createOrderPlaced(CustomerCommand command){
        OrderPlaced event=new OrderPlaced();
        event.setName(command.getOrderPlacedRequest().getName());
        event.setTabId(command.getOrderPlacedRequest().getTabId());
        event.setOrderType(command.getOrderPlacedRequest().getOrderType());
        event.setOrderStatus(OrderStatus.PLACED);
        event.setUuid(UUID.randomUUID());
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setEventType(EventType.ORDER_PLACED);
        Message<OrderPlaced> message= MessageBuilder.withPayload(event).build();
        eventProcessor.restaurant().send(message);
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(TabCreated event) {
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
        if(event.getEventType()== EventType.TAB_CLOSED){

            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> p.setOpen(false));
//            list.stream().forEach(i->{
//                if(i.getId().equals(event.getTabId()))
//                {
//                    i.setOpen(false);
//                }
//            });
//            for(int i=0;i<list.size();i++)
//            {
//                if(list.get(i).getId().equals(event.getTabId())){
//                    list.get(i).setOpen(false);
//                }
//            }
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderPlaced event) {
        if(event.getEventType()== EventType.ORDER_PLACED){

            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> {
                        if(p.isOpen()==true){
                        p.getList().add(event.toString());}
                                        });
        }
    }


}
