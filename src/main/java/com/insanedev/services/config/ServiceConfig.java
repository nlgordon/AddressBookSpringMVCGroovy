package com.insanedev.services.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.insanedev.repositories")
@ImportResource("classpath:/com/insanedev/config/root-context.xml")
@ComponentScan("com.insanedev.services")
public class ServiceConfig {

}
