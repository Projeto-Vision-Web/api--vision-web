package com.visionweb.app_vision_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class AppVisionWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppVisionWebApplication.class, args);
	}

}
