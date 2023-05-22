package myproject.ourmemory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OurmemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(OurmemoryApplication.class, args);
    }

}
