package com.leo;

import com.leo.entity.Player;
import com.leo.entity.Transaction;
import com.leo.repository.PlayerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@EnableSwagger2
public class Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.leo"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    @Bean
    CommandLineRunner loadData(PlayerRepository playerRepository) {
        return args -> {
            List<Transaction> list = new ArrayList<>();
            list.add(new Transaction(UUID.randomUUID().toString(), BigDecimal.ONE));

            log.info("Loading " + playerRepository.saveAndFlush(new Player("12345", BigDecimal.TEN, "Jhon", new ArrayList<>(list))));
            log.info("Loading " + playerRepository.saveAndFlush(new Player(UUID.randomUUID().toString(), BigDecimal.ONE, "Kate", new ArrayList<>())));
        };
    }
}
