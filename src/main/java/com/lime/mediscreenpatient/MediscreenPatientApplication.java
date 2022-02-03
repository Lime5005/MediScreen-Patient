package com.lime.mediscreenpatient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MediscreenPatientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediscreenPatientApplication.class, args);
	}

}
