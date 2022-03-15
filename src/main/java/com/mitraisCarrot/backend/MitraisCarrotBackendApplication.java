package com.mitraisCarrot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.mitraisCarrot.backend.spring.data.jpa.left.right.inner.cross.join.entity")
@EnableJpaRepositories("com.mitraisCarrot.backend.spring.data.jpa.left.right.inner.cross.join.repository")
public class MitraisCarrotBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MitraisCarrotBackendApplication.class, args);
	}

}
