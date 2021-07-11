package co.com.foodbank.packaged;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"co.com.foodbank.pckage", "co.com.foodbank.pckage.config",
        "co.com.foodbank.pckage.exception", "co.com.foodbank.pckage.repository",
        "co.com.foodbank.pckage.restcontroller",
        "co.com.foodbank.pckage.service",
        "co.com.foodbank.pckage.v1.controller",
        "co.com.foodbank.pckage.v1.model"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
