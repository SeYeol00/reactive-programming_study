package com.example.reactiveprogramming_study.fluxandmonogeneratorservice

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxAndMonoGeneratorServiceTest(

) {
    @Test
    fun namesFlux() {
        //given
        val fluxAndMonoGeneratorService:FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()
        //when
        var nameFlux: Flux<String> = fluxAndMonoGeneratorService.namesFlux()

        //then
        //여기서는 기존 junit 말고 reactor 라이브러리에서 사용하는 StepVerifier를 사용할 것이다.
        StepVerifier.create(nameFlux)
            .expectNext("alex")
            .expectNextCount(2)// Flux에 몇개가 남았는가
            .verifyComplete()
    }

    @Test
    fun nameMono() {
    }

    @Test
    fun namesFlux_map(){
        //given
        val fluxAndMonoGeneratorService:FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()
        var stringLength: Int = 3
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength)

        //then
        //여기서는 기존 junit 말고 reactor 라이브러리에서 사용하는 StepVerifier를 사용할 것이다.
        StepVerifier.create(namesFlux)
            .expectNext("4-ALEX")
            .expectNextCount(1)// Flux에 몇개가 남았는가
            .verifyComplete()
    }


    // Reactive Streams는 불변객체다
    @Test
    fun namesFlux_immutability(){
        //given
        val fluxAndMonoGeneratorService:FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_immutability()

        //then
        //여기서는 기존 junit 말고 reactor 라이브러리에서 사용하는 StepVerifier를 사용할 것이다.
        StepVerifier.create(namesFlux)
            .expectNext("alex")
            .expectNextCount(2)// Flux에 몇개가 남았는가
            .verifyComplete()
    }

    @Test
    fun namesFlux_flatmap() {
        //given
        val fluxAndMonoGeneratorService:FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()
        val stringLength: Int = 3
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap(stringLength)
        //then
        StepVerifier.create(namesFlux)
            .expectNext("A")
            .expectNextCount(8)// Flux에 몇개가 남았는가
            .verifyComplete()
    }

    @Test
    fun namesFlux_flatmap_async() {
        //given
        val fluxAndMonoGeneratorService:FluxAndMonoGeneratorService = FluxAndMonoGeneratorService()
        val stringLength: Int = 3
        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength)
        //then
        StepVerifier.create(namesFlux)
            .expectNextCount(9)// Flux에 몇개가 남았는가
            .verifyComplete()
    }
}