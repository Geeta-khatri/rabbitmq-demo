package com.impl.MQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

	@Autowired
	RabbitTemplate rabbitTemplet;
	
	@GetMapping("/test/{name}")
	public String testAPI(@PathVariable("name") String name ) {
		Person p=new Person(1L,name);
		rabbitTemplet.convertAndSend("Mobile", p);
		//this method convert and sends simple message converter which only supports string byte array and  serializable object
		return "success";
	}
}
