package pe.edu.cibertec.frontend_rt_fc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateAutenticacion(RestTemplateBuilder builder){
        return builder.rootUri("backend-dawii-hycscharebf9gvdp.brazilsouth-01.azurewebsites.net/user")
                .build();
    }
}
