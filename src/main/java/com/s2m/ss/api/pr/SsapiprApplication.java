package com.s2m.ss.api.pr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class SsapiprApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsapiprApplication.class, args);
	}

}