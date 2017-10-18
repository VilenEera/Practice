package com.vilen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringbootCodeTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCodeTemplateApplication.class, args);
	}
}
