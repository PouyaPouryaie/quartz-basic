package ir.bigz.spring.quratz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuratzApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuratzApplication.class, args);
	}

}
