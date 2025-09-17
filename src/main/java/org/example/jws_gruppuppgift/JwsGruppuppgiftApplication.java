package org.example.jws_gruppuppgift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class JwsGruppuppgiftApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwsGruppuppgiftApplication.class, args);
    }

}
