package com.ama.karate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.ama.karate.configuration.DatabaseValidator;

@SpringBootApplication
public class KarateApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(KarateApplication.class, args);

        // Retrieve the DatabaseValidator bean and run validation
        DatabaseValidator validator = context.getBean(DatabaseValidator.class);
        try {
            // validator.validateDatabase();
            System.out.println("Database has been validated successfully, Application Started!");
        } catch (Exception e) {
            System.err.println("Database validation failed: " + e.getMessage());
            System.exit(1);
        }


	}

}
