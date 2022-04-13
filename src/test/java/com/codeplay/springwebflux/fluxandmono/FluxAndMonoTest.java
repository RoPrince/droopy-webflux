package com.codeplay.springwebflux.fluxandmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

  @Test
  public void fluxTest() {
    Flux<String> stringFlux =
        Flux.just("Rubber", "Plant", "Humour")
            // .concatWith(Flux.error(new RuntimeException("Exception forced")))
            .concatWith(Flux.just("After Error"))
            .log();
    stringFlux.subscribe(
        System.out::println,
        (e) -> System.err.println(e),
        () -> System.out.println("On completion"));
  }

  @Test
  public void fluxTest_withoutError() {
    Flux<String> stringFlux = Flux.just("Rubber", "Plant", "Humour").log();
    StepVerifier.create(stringFlux)
        .expectNext("Rubber")
        .expectNext("Plant")
        .expectNext("Humour")
        // .expectNext("Humour1")
        .verifyComplete();
  }

  @Test
  public void fluxTest_withError() {
    Flux<String> stringFlux =
        Flux.just("Rubber", "Plant", "Humour")
            .concatWith(Flux.error(new RuntimeException("Exception forced")))
            .log();
    StepVerifier.create(stringFlux)
        .expectNext("Rubber")
        .expectNext("Plant")
        .expectNext("Humour")
        .expectError(RuntimeException.class)
        .verify();
  }

  @Test
  public void fluxTest_elementsCount() {
    Flux<String> stringFlux =
        Flux.just("Rubber", "Plant", "Humour")
            .concatWith(Flux.error(new RuntimeException("Exception forced")))
            .log();
    StepVerifier.create(stringFlux)
        .expectNextCount(3)
        .expectErrorMessage("Exception forced")
        .verify();
  }

  @Test
  public void monoTest() {
    Mono<String> stringMono = Mono.just("Snooze");
    StepVerifier.create(stringMono.log()).expectNext("Snooze").verifyComplete();
  }

  @Test
  public void monoTest_onError() {
    Mono<String> stringMono = Mono.error(new RuntimeException("Mono Exception"));
    StepVerifier.create(stringMono.log())
            .expectError(RuntimeException.class)
            .verify();
  }
}
