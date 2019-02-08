package com.ftn.nc.NCBackend;


import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableProcessApplication
public class NcBackendApplication {

	public static void main(String[] args) {
		
		System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk1.8.0_191\\jre\\lib\\security\\cacerts");
		
		SpringApplication.run(NcBackendApplication.class, args);
	}

}

