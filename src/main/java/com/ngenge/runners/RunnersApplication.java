package com.ngenge.runners;

import com.ngenge.runners.user.UserHttpClient;
import com.ngenge.runners.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@SpringBootApplication
public class RunnersApplication {
    private static final Logger log = LoggerFactory.getLogger(RunnersApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(RunnersApplication.class, args);
    }

    @Bean
    UserHttpClient userHttpClient() {
        RestClient restClient = RestClient.create("https://jsonplaceholder.typicode.com");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(UserHttpClient.class);
    }

    @Bean
    CommandLineRunner runner(
            //UserRestClient client
            UserHttpClient client
    ) {
        return args -> {
            System.out.println(client.findAll());
        };
    }

}
