package com.manyouwell.menu;

import com.manyouwell.menu.dao.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.manyouwell.menu"})
public class MenuApplication {
	public static void main(String[] args) {
		SpringApplication.run(MenuApplication.class, args);
	}
}
