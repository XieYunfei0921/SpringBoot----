package com.example.demo;

import org.hibernate.annotations.Parent;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class HierarchyApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder().sources(Parent.class)
				.child(Application.class)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
	}
}
