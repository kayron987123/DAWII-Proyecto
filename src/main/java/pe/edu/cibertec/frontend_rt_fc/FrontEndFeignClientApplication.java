package pe.edu.cibertec.frontend_rt_fc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FrontEndFeignClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrontEndFeignClientApplication.class, args);
	}

}
