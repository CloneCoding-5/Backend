package com.sparta.cloneproject_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(
        exclude = {
                org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration.class,
                org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration.class
        }
)
@EnableJpaAuditing
public class CloneProjectBeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloneProjectBeApplication.class, args);
    }
}
