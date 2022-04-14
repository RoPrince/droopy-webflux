package com.codeplay.springwebflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxAndMonoController {

  @GetMapping("/flux")
  public Flux<Integer> getFlux() {
    return Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(2)).log();
  }

  @GetMapping(value = "/fluxStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Integer> getFluxStream() {
    return Flux.just(1, 2, 3, 4).delayElements(Duration.ofSeconds(1)).log();
  }

  @GetMapping(value = "/fluxInfinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Long> getFluxInfiniteStream() {
    return Flux.interval(Duration.ofSeconds(1)).log();
  }

  @GetMapping(value = "/mono", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Mono<Integer> getMono() {
    return Mono.just(1).log();
  }
}
