package com.manyouwell.menu;

import com.manyouwell.menu.dao.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MenuApplication {
	public static void main(String[] args) {
		SpringApplication.run(MenuApplication.class, args);
	}
}
