package customer.services;

import com.sun.org.apache.xpath.internal.SourceTree;
import customer.aggregate.Order;
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
        list.stream()
                .filter(p->p.getId().equals(event.getTabId()))
                .findFirst()
                .ifPresent(p-> {
                    Message<TabClosed> message=MessageBuilder.withPayload(event).build();
                    eventProcessor.restaurant().send(message);
                });

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
        list.stream()
                .filter(p->p.getId().equals(event.getTabId()))
                .findFirst()
                .ifPresent(p-> {
                    if(p.isOpen()){
                        Message<OrderPlaced> message= MessageBuilder.withPayload(event).build();
                        eventProcessor.restaurant().send(message);}
                });
    }
    public void createOrderAccepted(CustomerCommand command){
        OrderAccepted event=new OrderAccepted();
        event.setUuid(UUID.randomUUID());
        event.setEventType(EventType.ORDER_ACCEPTED);
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setOrderId(command.getOrderAcceptedRequest().getOrderId());
        event.setTabId(command.getOrderAcceptedRequest().getTabId());
        list.stream()
                .filter(p->p.getId().equals(event.getTabId()))
                .findFirst()
                .ifPresent(p-> {
                    if(p.isOpen() && event.getOrderId().equals(command.getOrderAcceptedRequest().getOrderId())) {
                        p.getOrders().stream().filter(k->k.getUuid().toString().equals(event.getOrderId()))
                                .findFirst().ifPresent(k->
                                        {if(k.getOrderStatus()==OrderStatus.SERVED){
                                            Message<OrderAccepted> message=MessageBuilder.withPayload(event).build();
                                            eventProcessor.restaurant().send(message);
                                        }}
                                );
                    }
                });

    }
    public void createOrderDeclined(CustomerCommand command){
        OrderDeclined event=new OrderDeclined();
        event.setUuid(UUID.randomUUID());
        event.setEventType(EventType.ORDER_DECLINED);
        event.setTimeCreated(dateFormat.format(new Date()));
        event.setOrderId(command.getOrderDeclinedRequest().getOrderId());
        event.setTabId(command.getOrderDeclinedRequest().getTabId());
        list.stream()
                .filter(p->p.getId().equals(event.getTabId()))
                .findFirst()
                .ifPresent(p-> {
                    if(p.isOpen() && event.getOrderId().equals(command.getOrderDeclinedRequest().getOrderId())) {
                        p.getOrders().stream().filter(k->k.getUuid().toString().equals(event.getOrderId()))
                                .findFirst().ifPresent(k->
                                {if(k.getOrderStatus()==OrderStatus.SERVED){
                                    Message<OrderDeclined> message=MessageBuilder.withPayload(event).build();
                                    eventProcessor.restaurant().send(message);
                                }}
                        );
                    }
                });
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
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderPlaced event) {
        if(event.getEventType()== EventType.ORDER_PLACED){
            Order order=new Order();
            order.setName(event.getName());
            order.setUuid(event.getUuid());
            order.setTimeCreated(event.getTimeCreated());
            order.setOrderStatus(event.getOrderStatus());
            order.setOrderType(event.getOrderType());
            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> {
                        if(p.isOpen()==true){
                        p.getOrders().add(order);}
                                        });
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderServed event) {
        if(event.getEventType()== EventType.ORDER_SERVED){
            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> { p.getOrders().stream().filter(k->k.getUuid().toString().equals(event.getOrderId()))
                                .findFirst()
                                .ifPresent(k->k.setOrderStatus(OrderStatus.SERVED));
                    });
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderAccepted event) {
        if(event.getEventType()== EventType.ORDER_ACCEPTED){
            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> { p.getOrders().stream().filter(k->k.getUuid().toString().equals(event.getOrderId()))
                            .findFirst()
                            .ifPresent(k->k.setOrderStatus(OrderStatus.ACCEPTED));
                    });
        }
    }
    @StreamListener(Channel.RESTAURANT_CHANNEL_IN_NAME)
    private void on(OrderDeclined event) {
        if(event.getEventType()== EventType.ORDER_DECLINED){
            list.stream()
                    .filter(p->p.getId().equals(event.getTabId()))
                    .findFirst()
                    .ifPresent(p-> { p.getOrders().stream().filter(k->k.getUuid().toString().equals(event.getOrderId()))
                            .findFirst()
                            .ifPresent(k->k.setOrderStatus(OrderStatus.DECLINED));
                    });
        }
    }

}
