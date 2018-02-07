package customer;

import customer.events.EventProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(EventProcessor.class)
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
