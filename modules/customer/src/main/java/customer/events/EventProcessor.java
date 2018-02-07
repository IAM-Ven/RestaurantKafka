package customer.events;

import customer.channelProperties.Channel;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface EventProcessor {
    @Output(Channel.RESTAURANT_CHANNEL_NAME)
    MessageChannel restaurant();
}
