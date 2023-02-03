package com.multi.ukids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class UkidsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UkidsApplication.class, args);
		System.out.println("유키즈에 오신걸 환영합니다!!!");
	}

}
