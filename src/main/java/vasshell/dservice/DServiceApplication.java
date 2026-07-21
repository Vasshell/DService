package vasshell.dservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DServiceApplication.class, args);
    }

}
