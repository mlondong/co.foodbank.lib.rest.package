package co.com.foodbank.packaged;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import co.com.foodbank.stock.sdk.config.EnableStockSDK;

@SpringBootApplication
@EnableStockSDK
@ComponentScan({"co.com.foodbank.packaged", "co.com.foodbank.packaged.config",
        "co.com.foodbank.packaged.exception",
        "co.com.foodbank.packaged.repository",
        "co.com.foodbank.packaged.restcontroller",
        "co.com.foodbank.packaged.service",
        "co.com.foodbank.packaged.v1.controller",
        "co.com.foodbank.packaged.util", "co.com.foodbank.packaged.v1.model"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
