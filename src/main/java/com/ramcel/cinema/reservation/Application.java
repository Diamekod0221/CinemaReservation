package com.ramcel.cinema.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication()
@ComponentScan(basePackages = "com.ramcel.cinema.reservation")
@EntityScan("com.ramcel.cinema.reservation")
public class Application {

	public static void main(String[] args) {SpringApplication.run(Application.class, args);
	}

}
