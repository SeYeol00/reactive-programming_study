package com.example.reactiveprogramming_study.fluxandmonogeneratorservice

import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.time.Duration
import java.util.Random

class FluxAndMonoGeneratorService {
    private val log = LoggerFactory.getLogger(javaClass)

    fun namesFlux(): Flux<String>{
        val namesList: List<String> = listOf<String>("alex", "ben", "chloe")
        //return Flux.just("alex", "ben", "chloe")
        return Flux.fromIterable(namesList) // db or a remote service call
            .log() // 웹플럭스를 통해 데이터 로깅이 가능하다 onNext 함수를 보여줌
    }

    fun nameMono():Mono<String>{
        // Mono는 하나 혹은 0개니까 just
        return Mono.just("alex")
            .log()
    }

    // map은 기존 가져온 데이터를 새 형상으로 트랜스포메이션하는 방법
    fun namesFlux_map(stringLength: Int): Flux<String>{
        // filter the string whose length is greater than 3
        val namesList: List<String> = listOf<String>("alex", "ben", "chloe")
        //return Flux.just("alex", "ben", "chloe")
        return Flux.fromIterable(namesList) // db or a remote service call
            .map { name -> name.uppercase() }
            .filter{name -> name.length > stringLength} // 4 - ALEX, 5 - CHLOE
            .map { name -> name.length.toString() + "-" + name }
            .log() // 웹플럭스를 통해 데이터 로깅이 가능하다 onNext 함수를 보여줌
    }

    fun namesFlux_immutability(): Flux<String>{
        val namesList: List<String> = listOf<String>("alex", "ben", "chloe")
        //return Flux.just("alex", "ben", "chloe")
        val namesFlux = Flux.fromIterable(namesList) // db or a remote service call
        namesFlux.map { name -> name.uppercase() }
        return namesFlux
    }

    fun namesFlux_flatmap(stringLength: Int): Flux<String>{
        // filter the string whose length is greater than 3
        //return Flux.just("alex", "ben", "chloe")
        return Flux.fromIterable(listOf<String>("alex", "ben", "chloe")) // db or a remote service call
            .map { name -> name.uppercase() }
            .filter{name -> name.length > stringLength} // 4 - ALEX, 5 - CHLOE
            // ALEX, CHLOE -> A, L, E, X, C, H, L, O, E
            .flatMap { name -> splitString(name) } // A, L, E, X, C, H, L, O, E
            .log() // 웹플럭스를 통해 데이터 로깅이 가능하다 onNext 함수를 보여줌
    }

    fun namesFlux_flatmap_async(stringLength: Int): Flux<String>{
        // filter the string whose length is greater than 3
        //return Flux.just("alex", "ben", "chloe")
        return Flux.fromIterable(listOf<String>("alex", "ben", "chloe")) // db or a remote service call
            .map { name -> name.uppercase() }
            .filter{name -> name.length > stringLength} // 4 - ALEX, 5 - CHLOE
            // ALEX, CHLOE -> A, L, E, X, C, H, L, O, E
            .flatMap { name -> splitStringWithDelay(name) } // A, L, E, X, C, H, L, O, E
            .log() // 웹플럭스를 통해 데이터 로깅이 가능하다 onNext 함수를 보여줌
    }

    private fun splitString(name: String): Flux<String> {
        val charArray = name.split("").toFlux()
        log.info("charArray = {}", charArray)
        return charArray
    }
    private fun splitStringWithDelay(name: String): Flux<String> {
        var delay = kotlin.random.Random(0).nextLong(1000)
        val charArray = name.split("").toFlux()
            .delayElements(Duration.ofMillis(delay))
        log.info("charArray = {}", charArray)
        return charArray
    }




}