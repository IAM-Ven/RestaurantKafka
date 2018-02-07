package customer.events;

import customer.channelProperties.Channel;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EventProcessor {
    @Output(Channel.RESTAURANT_CHANNEL_OUT_NAME)
    MessageChannel restaurant();

    @Input(Channel.RESTAURANT_CHANNEL_IN_NAME)
    SubscribableChannel restaurantIn();
}
