package com.codeplay.springwebflux.handler;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class HandlerFunctionTest {

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate().responseTimeout(Duration.ofMillis(30000)).build();
    }


    @Test
    public void testApproach1() {
        Flux<Integer> integerFlux =
                webTestClient
                        .get()
                        .uri("/functional/flux")
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Integer.class)
                        .getResponseBody();

        StepVerifier.create(integerFlux).expectSubscription().expectNext(1, 2, 3, 4).verifyComplete();
    }


}
