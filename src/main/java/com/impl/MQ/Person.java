package com.impl.MQ;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable{
//implementing this so as to send it in queue if not done then it will throw error  
	private Long id;
	private String name;
	
	
	//rabbitmq-plugins.bat enable rabbitmq_management
	//used this for rabit mq 
	
	
}
