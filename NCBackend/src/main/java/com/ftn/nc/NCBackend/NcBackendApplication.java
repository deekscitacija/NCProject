package com.ftn.nc.NCBackend;


import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class NcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NcBackendApplication.class, args);
	}

}

