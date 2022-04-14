package com.codeplay.springwebflux.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest()
public class FluxAndMonoControllerTest {

  @Autowired WebTestClient webTestClient;

  @BeforeEach
  public void setUp() {
    webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build();
  }

  @Test
  public void testApproach1() {
    Flux<Integer> integerFlux =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Integer.class)
            .getResponseBody();

    StepVerifier.create(integerFlux).expectSubscription().expectNext(1, 2, 3, 4).verifyComplete();
  }

  @Test
  public void testApproach2() {
    webTestClient
        .get()
        .uri("/flux")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .isOk()
        .expectBodyList(Integer.class)
        .hasSize(4);
  }

  @Test
  public void testApproach3() {

    List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4);

    EntityExchangeResult<List<Integer>> entityExchangeResult =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectStatus()
            .isOk()
            .expectBodyList(Integer.class)
            .returnResult();
    assertEquals(expectedResult, entityExchangeResult.getResponseBody());
  }

  @Test
  public void testApproach4() {

    List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4);

    webTestClient
        .get()
        .uri("/flux")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectHeader()
        .contentType(MediaType.APPLICATION_JSON)
        .expectStatus()
        .isOk()
        .expectBodyList(Integer.class)
        .consumeWith(response -> assertEquals(expectedResult, response.getResponseBody()));
  }

  @Test
  public void testFluxStreamInfinite() {

    Flux<Long> longFlux =
        webTestClient
            .get()
            .uri("/flux")
            .accept(MediaType.valueOf(MediaType.APPLICATION_STREAM_JSON_VALUE))
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Long.class)
            .getResponseBody();

    StepVerifier.create(longFlux)
        .expectNext(1l)
        .expectNext(2l)
        .expectNext(3l)
        .thenCancel()
        .verify();
  }

  @Test
  public void testMono() {

    Integer expectedValue = 1;

    webTestClient
        .get()
        .uri("/mono")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Integer.class)
        .consumeWith((response) -> Assert.assertEquals(expectedValue, response.getResponseBody()));
  }
}
