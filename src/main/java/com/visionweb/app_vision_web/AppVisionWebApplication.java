package com.visionweb.app_vision_web;

import com.visionweb.app_vision_web.domain.core.entities.jwt.JwtSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties(JwtSettings.class)
public class AppVisionWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppVisionWebApplication.class, args);
	}

}
