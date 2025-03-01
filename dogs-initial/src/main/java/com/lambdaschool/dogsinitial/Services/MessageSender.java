package com.lambdaschool.dogsinitial.Services;

import com.lambdaschool.dogsinitial.DogsinitialApplication;

import com.lambdaschool.dogsinitial.model.Dog;
import com.lambdaschool.dogsinitial.model.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageSender
{
    private RabbitTemplate rt;
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);


    public MessageSender(RabbitTemplate rt)
    {
        this.rt = rt;
    }


    @Scheduled(fixedDelay = 3000L)
    public void sendMessage()
    {
        for (Dog e : DogsinitialApplication.ourDogList.dogList)
        {
            int rand = new Random().nextInt(10); // 0 - 9
            boolean randBool = new Random().nextBoolean();
            MessageDetail message = new MessageDetail(e.toString(), rand, randBool);

            if (rand <= 5)
            {
                logger.info("Sending message HIGH");
                rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH, message);
            } else
            {
                logger.info("Sending message LOW");
                rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_LOW, message);
            }
        }
    }
}
