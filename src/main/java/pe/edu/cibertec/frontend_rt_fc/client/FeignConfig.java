package pe.edu.cibertec.frontend_rt_fc.client;

import feign.Client;
import feign.Request;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(10000, 10000);
    }

}
