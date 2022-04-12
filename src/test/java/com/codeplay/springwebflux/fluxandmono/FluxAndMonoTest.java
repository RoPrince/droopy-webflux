package com.codeplay.springwebflux.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
      Flux<String> stringFlux = Flux.just("Rubber", "Plant","Humour")
              .concatWith(Flux.error(new RuntimeException("Exception forced")))
              .log();
      stringFlux.subscribe(System.out::println,(e)->System.err.println(e));
  }
}
