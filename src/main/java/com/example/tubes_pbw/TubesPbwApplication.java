package com.example.tubes_pbw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TubesPbwApplication {

	public static void main(String[] args) {
		SpringApplication.run(TubesPbwApplication.class, args);
	}

}
