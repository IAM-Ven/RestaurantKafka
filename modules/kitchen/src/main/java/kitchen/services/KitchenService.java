package kitchen.services;

import kitchen.aggregate.Order;
import kitchen.aggregate.Tab;
import kitchen.channelProperties.Channel;
import kitchen.events.*;
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
public class KitchenService {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static List<Tab> list= new ArrayList();
    private EventProcessor eventProcessor;
    @Autowired
    public KitchenService(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public List<Tab>getAll(){
        return list;
    }

    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(TabCreated event) {
        if(event.getEventType()== EventType.TAB_CREATED){
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
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderPlaced event) {
        if(event.getEventType()== EventType.ORDER_PLACED){
            OrderServed order=new OrderServed();
            order.setUuid(UUID.randomUUID());
            order.setTimeCreated(dateFormat.format(new Date()));
            order.setEventType(EventType.ORDER_SERVED);
            order.setOrderId(event.getUuid().toString());
            order.setTabId(event.getTabId());
            Message<OrderServed> message= MessageBuilder.withPayload(order).build();
            eventProcessor.restaurant().send(message);
            event.setOrderStatus(OrderStatus.SERVED);
            Order orderAg=new Order();
            orderAg.setName(event.getName());
            orderAg.setUuid(event.getUuid());
            orderAg.setTimeCreated(event.getTimeCreated());
            orderAg.setOrderStatus(event.getOrderStatus());
            orderAg.setOrderType(event.getOrderType());
            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> {
                        if(p.isOpen()==true){
                            p.getList().add(orderAg);}
                    });
        }
    }

}
