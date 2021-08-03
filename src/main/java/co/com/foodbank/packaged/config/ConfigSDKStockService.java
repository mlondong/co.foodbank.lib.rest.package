package co.com.foodbank.packaged.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import co.com.foodbank.stock.sdk.service.SDKStockService;

/**
 * @author mauricio.londono@gmail.com co.com.foodbank.packaged.config 12/07/2021
 */

@Configuration
@Qualifier("sdkStock")
public class ConfigSDKStockService {
    @Bean
    public SDKStockService sdkStock() {
        return new SDKStockService();
    }
}
