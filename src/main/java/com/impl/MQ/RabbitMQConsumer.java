package com.impl.MQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

	//to define a single listner
	//w have defined this containerFactory
	@RabbitListener(queues="Mobile" ,containerFactory = "rabbitListenerContainerFactory")
	public void getMessage(Person p) {
		System.out.println(p.getName());
	}
	
	//to to apply multiple @RabbitListener annotations to the same method.
	@RabbitListeners({
		@RabbitListener(queues="AC"),
		@RabbitListener(queues= "TV")
	})
	public void receive() {
		
	}
	
	//above can also be used as 
	/*@RabbitListener(queues = "orderQueue")
	@RabbitListener(queues = "paymentQueue")
	public void receive(String msg) {
	}*/
}
