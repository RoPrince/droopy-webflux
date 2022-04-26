package com.codeplay.springwebflux.router;

import com.codeplay.springwebflux.handler.SampleHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterFunctionConfig {

  @Bean
  public RouterFunction<ServerResponse> route(SampleHandlerFunction handlerFunction) {
    return RouterFunctions.route(
            RequestPredicates.GET("/functional/flux"), handlerFunction::getFluxResponse)
        .andRoute(RequestPredicates.GET("/functional/mono"), handlerFunction::getMonoResponse);
  }
}
