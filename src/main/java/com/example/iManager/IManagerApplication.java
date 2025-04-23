package com.example.iManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;

@SpringBootApplication
public class IManagerApplication {

	public static void main(String[] args) {
//		ConfigurableApplicationContext context =
				SpringApplication.run(IManagerApplication.class, args);

/*
		String[] beans = context.getBeanDefinitionNames();
		for(String bean : beans){
			System.out.println(bean);
		}
*/
    }

}
