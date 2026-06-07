package com.impl.MQ;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@SpringBootApplication
public class RabbitMqImplApplication {

	public static void main(String[] args)  throws IOException,TimeoutException{
		SpringApplication.run(RabbitMqImplApplication.class, args);
		String msg="first msg for RabbitMQ";
		//for publishing 
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection=factory.newConnection();
		Channel channel=connection.createChannel();
		channel.basicPublish("", "Queue---1", null, msg.getBytes());//to publish to queue provided queue name 
		
		JSONObject json=new JSONObject();
		json.put("name", "abc");
		json.put("email", "abc@gmail.com");
		json.put("query", "select * from user ");
		channel.basicPublish("", "Queue--1", null, json.toString().getBytes());
		
		//direct exchange example
		String direct_msg="this is mobile";
		channel.basicPublish("Direct-Exchange", "mobile", null, direct_msg.getBytes());//provided direct name
		
		//fanout-exchange example
		String fanOut_msg="msg only for AC and Mobile";
		channel.basicPublish("Fanout-Exchange", "", null, fanOut_msg.getBytes());
		 
		
		//for topic excahnge
		
		String topic_msg="topic msg for mobile and ac";
		channel.basicPublish("Topic-Exchange", "tv.mobile.ac", null, topic_msg.getBytes());
		
		//for headers exchange
		String headers_msg="headers msg for mobile and tv";
		Map<String,Object> headersMap=new HashMap<String,Object>();
		headersMap.put("item1", "mobile");
		headersMap.put("item2", "television");
		BasicProperties br=new BasicProperties();
		br=br.builder().headers(headersMap).build();
		channel.basicPublish("Headers-Exchange", "", br, headers_msg.getBytes());
		
		channel.close();
		connection.close();
		
		//for consuming  this will be same for eveyr scneio as consumer delas with queue no exchange
		//to cosume msg from queue
		//DeliverCallback is a functional interface used when consuming messages asynchronously
		Connection connection1=factory.newConnection();
		Channel channel1=connection1.createChannel();
		DeliverCallback deliverCallback=(consumerTag,delivery)->{
			String message=new String(delivery.getBody());
			System.out.println("Message received "+message);
		};
		
		//channel1.basicConsume( "Queue--1","true", deliverCallback, consumerTag->{});
		channel1.basicConsume("Queue--1", true, deliverCallback, consumerTag->{});
		
		//SpringApplication.run(RabbitMqImplApplication.class, args);
	}

}
