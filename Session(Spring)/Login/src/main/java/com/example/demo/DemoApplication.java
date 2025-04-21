package com.example.demo;

import com.example.demo.Util.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		JwtUtil jwt = new JwtUtil();

		String token = jwt.generateToken("wl39");
		System.out.println(token);
		System.out.println(jwt.generateToken("wl39"));
		System.out.println(jwt.validateTokenAndGetUsername(token));
	}

}
