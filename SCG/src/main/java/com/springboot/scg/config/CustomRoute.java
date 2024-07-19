package com.springboot.scg.config;

import com.springboot.scg.component.L1Filter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRoute {

    private final L1Filter l1Filter;

    public CustomRoute(L1Filter l1Filter) {
        this.l1Filter = l1Filter;
    }

    @Bean
    public RouteLocator cRoute(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("ms1", r -> r.path("/ms1/**")
                        .filters(f -> f.filter(l1Filter.apply(new L1Filter.Config(true, true))))
                        .uri("lb://MS1"))
                .route("ms2", r -> r.path("/ms2/**")
                        .uri("http://localhost:8082"))
                .build();
    }
}
