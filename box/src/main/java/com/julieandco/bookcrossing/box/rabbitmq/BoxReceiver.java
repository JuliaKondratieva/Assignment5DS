package com.julieandco.bookcrossing.box.rabbitmq;

import com.julieandco.bookcrossing.box.entity.Box;
import com.julieandco.bookcrossing.box.entity.dto.BoxDTO;
import com.julieandco.bookcrossing.box.service.BoxService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BoxReceiver {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final BoxService boxService;
    @Autowired
    public BoxReceiver(BoxService boxService) {
        this.boxService = boxService;
    }
    private final static String QUEUE_NAME = "boxqueue";

    @RabbitListener(queues = QUEUE_NAME)
    public void consume(BoxDTO boxDTO) {
        Box box=new Box();
        box.setAddress(boxDTO.getAddress());
        System.out.println("CONSUMER TRIGGERED");
        boxService.addBox(box);
    }
}
