package com.fede.alk.back.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PasswordEncoder passwordEncoder=new Argon2PasswordEncoder(16, 32, 3, 64 * 1024, 3);
		System.out.println(passwordEncoder.encode("fede123"));
	}
}
