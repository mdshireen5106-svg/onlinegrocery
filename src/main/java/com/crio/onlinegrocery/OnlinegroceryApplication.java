package com.crio.onlinegrocery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class OnlinegroceryApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinegroceryApplication.class, args);
		System.out.println("Onilne Grocery Order Management");
	}

}
