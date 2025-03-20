package com.tea.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.tea.demo", "com.tea.demo.product"})
public class TeaManagementApplication {
	// private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TeaManagementApplication.class, args);
//		log.info("hello world");
	}

}

